import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
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
	private boolean isCanceled = false;
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
			new Thread(new finder()).start();
		}
	}
	
	private void updateText()
	{
		validate();
	}
	
	private class finder implements Runnable
	{
		ArrayList<Integer> primes = new ArrayList<Integer>();
		public void run()
		{
			float startTime = System.currentTimeMillis();
			float someTimePassed;
			int num = Integer.parseInt(input.getText());
			try
			{
				//
				if (num > 1)
				{
					while(!isCanceled)
					{
						//for every number 'i' to 'num', judge if i is a prime.
						for(int i = 2; i <= num; i++) 
						{
							someTimePassed = System.currentTimeMillis() - startTime;
							
							//if there is no value 'j' that makes "i % j == 0" true, then i is a prime.
							for(int j = 2; j < i/2; j++)
							{
								if(i % j > 0)
								{
									if ((System.currentTimeMillis() - someTimePassed) > 5000f)
									{
										output.setText("Found " + primes.size() + " in " + i + " of " + num + " " + (System.currentTimeMillis() - startTime)/1000f);
										updateText();
									}
									continue;
								}
								else
								{
									status.append("" + i + "\n");
									primes.add(i);
									if ((System.currentTimeMillis() - someTimePassed) > 5000f)
									{
										output.setText("Found " + primes.size() + " in " + i + " of " + num);
									}
									updateText();
									break;
								}
							}
						}
					break;
					}
				}
				float endTime = System.currentTimeMillis();
				float totalTime = (endTime - startTime)/1000f;
				status.append("Total time: " + totalTime);
			}
			catch(Exception e)
			{
				//
			}
			cancel.setEnabled(false);
			start.setEnabled(true);
			input.setEnabled(true);
		}
	}
	
	PrimeNumberFinder()
	{
		super("Prime Number Finder");
		setLocationRelativeTo(null);
		setSize(400,400);
		setVisible(true);
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
		
		
	}
	
	public static void main(String[] args) {
		new PrimeNumberFinder();
	}
}
