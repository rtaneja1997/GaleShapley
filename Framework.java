//Name: Ritvik Taneja
//NetID: rt328


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;


public class Framework
{
	int n; // number of men (women)

	int MenPrefs[][]; // preference list of men (n*n)
	int WomenPrefs[][]; // preference list of women (n*n)

	ArrayList<MatchedPair> MatchedPairsList; // your output should fill this arraylist which is empty at start

	public class MatchedPair
	{
		int man; // man's number
		int woman; // woman's number

		public MatchedPair(int Man,int Woman)
		{
			man=Man;
			woman=Woman;
		}

		public MatchedPair()
		{
		}
	}

	// reading the input
	void input(String input_name)
	{
		File file = new File(input_name);
		BufferedReader reader = null;

		try
		{
			reader = new BufferedReader(new FileReader(file));

			String text = reader.readLine();

			String [] parts = text.split(" ");
			n=Integer.parseInt(parts[0]);

			MenPrefs=new int[n][n];
			WomenPrefs=new int[n][n];

			for (int i=0;i<n;i++)
			{
				text=reader.readLine();
				String [] mList=text.split(" ");
				for (int j=0;j<n;j++)
				{
					MenPrefs[i][j]=Integer.parseInt(mList[j]);
				}
			}

			for (int i=0;i<n;i++)
			{
				text=reader.readLine();
				String [] wList=text.split(" ");
				for(int j=0;j<n;j++)
				{
					WomenPrefs[i][j]=Integer.parseInt(wList[j]);
				}
			}

			reader.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// writing the output
	void output(String output_name)
	{
		try
		{
			PrintWriter writer = new PrintWriter(output_name, "UTF-8");

			for(int i=0;i<MatchedPairsList.size();i++)
			{
				writer.println(MatchedPairsList.get(i).man+" "+MatchedPairsList.get(i).woman);
			}

			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public Framework(String []Args)
	{
		input(Args[0]);

		MatchedPairsList=new ArrayList<MatchedPair>(); // you should put the final stable matching in this array list

		/* NOTE
		 * if you want to declare that man x and woman y will get matched in the matching, you can
		 * write a code similar to what follows:
		 * MatchedPair pair=new MatchedPair(x,y);
		 * MatchedPairsList.add(pair);
		*/

		//YOUR CODE STARTS HERE
		ArrayList<Integer> free_men = new ArrayList<Integer>();
		ArrayList<Integer> free_women = new ArrayList<Integer>();
		
		for (int id=0; id < n; id ++) { //populate lists 
			free_men.add(id);
			free_women.add(id);
			
		}
		
		int[] curr_women = new int[n]; //keeps track of most recent woman for each man to propose to
									   //curr_women[i] corresponds to man i 
		
		
		

		while (free_men.size() > 0) { //while there exists an unmatched man 
			int m = free_men.remove(0); //get first unmatched man
			int w = MenPrefs[m][curr_women[m]]; //get m's current preference 
			curr_women[m] ++; //update recent woman
			
			if (free_women.contains(w)) { //woman is unmatched
				int w_index = 0; 
				for (Integer curr_woman: free_women) {
					if (curr_woman == w) {
						break;
					}
					w_index ++;
				}
				
				MatchedPair newpair = new MatchedPair(m,w);
				MatchedPairsList.add(newpair);
				
				free_women.remove(w_index);
				
			}
			else { //woman has already been matched
				int curr_man = 0;
				int index = 0; 
				boolean found = false;
				for (MatchedPair p: MatchedPairsList) {
					
					if (p.woman == w) {
						curr_man = p.man;
						found = true;
					}
					if (!found) {
						index ++;
					}
					
					
				}
				
			  	//check if woman prefers current matching
				int[] wprefs = WomenPrefs[w];
				boolean satisfied = true; 

				for (int i=0; i < n; i ++) {
					if (wprefs[i] == curr_man) { //prefers matching 
						break;
					}
					if (wprefs[i] == m) { //doesn't prefer matching 
						satisfied = false;
						break;
					}
				}
				if (!satisfied) { //woman wants to leave current matching
					MatchedPairsList.remove(index);
					
					MatchedPair newpair = new MatchedPair(m, w);
					MatchedPairsList.add(newpair);
					
					free_men.add(curr_man);
					
				}else { //m was not matched
					free_men.add(m);
					
				}	
			}
		}
		//YOUR CODE ENDS HERE

		output(Args[1]);
	}

	public static void main(String [] Args) // Strings in Args are the name of the input file followed by the name of the output file
	{
		
		new Framework(Args);
	}
}
