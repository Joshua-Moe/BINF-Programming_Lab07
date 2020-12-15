import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
//import javax.swing.JLabel;
import java.util.ArrayList;

public class PrimeNumberFinder extends JFrame 
{
	private static final long serialVersionUID = 1L;
	
	private JButton start = new JButton("Start");
	private JButton cancel = new JButton("Cancel");
	volatile private boolean isCanceled = false;
	private JTextArea status = new JTextArea();
	private JScrollPane jsp;
	private JTextField input = new JTextField("Add number here");
	private JTextField output = new JTextField();
	//private JButton submit = new JButton("Submit");
	
	
	private JPanel topPanel()
	{
		JPanel top = new JPanel();
		top.setLayout(new GridLayout(0,1));
		top.add(input);
		//top.add(submit);
		return top;
	}
	
	private JPanel centerPanel()
	{
		JPanel centerP = new JPanel();
		centerP.setLayout(new GridLayout(0,2));
		centerP.add(output);
		centerP.add(jsp);
		return centerP;
	}
	
	private JPanel buttonPanel()
	{
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(0,2));
		buttons.add(start);
		buttons.add(cancel);
		return buttons;
	}
	
	// Add prime number method here
	private class primeNumbers implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			cancel.setEnabled(true);
			start.setEnabled(false);
			input.setEnabled(false);
			status.setText("");
			output.setText("");
			new Thread(new finder()).start();
		}
	}
	
	private void updateText()
	{
		validate();
	}
	
	
	private class finder implements Runnable
	{
		volatile long startTime = System.currentTimeMillis();
		//long someTimePassed = 0;
		volatile long timePointA = System.currentTimeMillis();
		volatile long timePointB;
		volatile long saveTimePoint = 0;
		
		private void setMessage(String s, long i, long num)
		{
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if ((timePointB - timePointA)/1000.0 > 5.0) 
					{ 
						saveTimePoint += (timePointB - timePointA);
						output.setText("Found " + primes.size() + " in " + i + " of " + num + " " + (saveTimePoint)/1000f + " s");
						updateText();
						timePointA = System.currentTimeMillis();
					}
				}
			});
		}
		
		
		
		ArrayList<Long> primes = new ArrayList<Long>();
		public void run()
		{
			long num = Long.parseLong(input.getText());
			
			//for every number 'i' to 'num', judge if i is a prime.
			for(long i = 1; i <= num && !isCanceled; i++) 
			{
				timePointB = System.currentTimeMillis();
				boolean isCom = false;
				
				//if there is no value 'j' that makes "i % j == 0" true, then i is a prime.
				for(long j = 2; j <= i/2 && !isCom; j++)
				{
					if(i % j == 0) //if com
					{
						isCom = true;
					}
					
				}
				timePointB = System.currentTimeMillis();
					
				if(!isCom)
				{
					status.append("" + i + "\n");
					primes.add(i);
					setMessage("h", i, num);
					//isComposite = true;
				}
				
			}
			
			
			long endTime = System.currentTimeMillis();
			long totalTime = (endTime - startTime)/1000l;
			output.setText("Found " + primes.size() + " in " + num + " of " + num + " " + totalTime + " s");
			status.append("Total time: " + totalTime);
			updateText();
			
			cancel.setEnabled(false);
			start.setEnabled(true);
			input.setEnabled(true);
			isCanceled = false;
		}
	}
	
	PrimeNumberFinder()
	{
		super("Prime Number Finder");
		//setLocationRelativeTo(null);
		//setSize(400,400);
		//setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(topPanel(), BorderLayout.NORTH);
		jsp = new JScrollPane(status, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		getContentPane().add(centerPanel(), BorderLayout.CENTER);
		getContentPane().add(buttonPanel(), BorderLayout.SOUTH);
		start.addActionListener(new primeNumbers());
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if (isCanceled == true) {
					isCanceled = false;
				}
				else
				{
					isCanceled = true;
				}
			}
		});
		
		setLocationRelativeTo(null);
		setSize(600,400);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new PrimeNumberFinder();
	}
}
