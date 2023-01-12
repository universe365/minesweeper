package m3;

import java.util.Scanner;
import java.awt.EventQueue;

public class Main {
	
	private int total_size=5;
	private int bombqty=3;
	private int copyqty=bombqty;
	private int copyrealqty=bombqty;
 
	private final String[] label= {"[ ]","[1]","[2]","[3]","[4]","[5]","[6]","[7]","[8]","[X]","[?]","[0]"};
	
	private int[][] bombmap = null;
	private int[][] map= null;
	private int[][] coveredmap=new int[total_size][total_size];
	private int[][] presscount;
	
	public void init() {
		map= new int[total_size][total_size];
		bombmap = new int[total_size][total_size];
		coveredmap = new int[total_size][total_size];
	}
	 
	public void printbombMap() {
		System.out.println("\n-----------------START-----------------");
		for(int[] index:bombmap) {
			for(int i:index) {
				System.out.print(i+" ");
			}
			System.out.println();
		}
		System.out.println("\n-----------------END-----------------");
	}
	public void printMap() {
		System.out.println("\n______________MAP START______________");
		for(int[] index:map) {
			for(int i:index) {
				System.out.print(i+" ");
			}
			System.out.println();
		}
		System.out.println("\n______________MAP END______________");
	}
	public void printCoveredMap() {
		System.out.println("\n______________CoveredMAP START______________");
		for(int[] index:coveredmap) {
			for(int i:index) {
				System.out.print(i+" ");
			}
			System.out.println();
		}
		System.out.println("\n______________CoveredMAP END______________");
	}
	public void displayGame() {
		boolean fail=false;
		boolean win=false;
		presscount=new int[total_size][total_size];
		
		System.out.println("\n炸彈數量:"+copyqty);
		
		System.out.print("y/x");
		for(int i=0; i<total_size; i++) {
			System.out.print(" "+i+" ");
		}
		System.out.println();
		
		for(int i=0; i<coveredmap.length; i++) {
			System.out.print(i+"  ");
			
			for(int j=0; j<coveredmap.length; j++) {
				
				if(coveredmap[i][j]==0 && presscount[i][j]==0) {
					
					System.out.print(label[0]);
				}else
				if(coveredmap[i][j]==11){
					
					System.out.print(label[11]);
				}else
				if(coveredmap[i][j]==1) {
					System.out.print(label[1]);
				}else
				if(coveredmap[i][j]==2) {
					System.out.print(label[2]);
				}else
				if(coveredmap[i][j]==3) {
					System.out.print(label[3]);
				}else
				if(coveredmap[i][j]==4) {
					System.out.print(label[4]);
				}else
				if(coveredmap[i][j]==5) {
					System.out.print(label[5]);
				}else
				if(coveredmap[i][j]==6) {
					System.out.print(label[6]);
				}else
				if(coveredmap[i][j]==7) {
					System.out.print(label[7]);
				}else
				if(coveredmap[i][j]==8) {
					System.out.print(label[8]);
				}else
				if(coveredmap[i][j]==9) {
					System.out.print(label[9]);
					fail=true;
				}else
				if(coveredmap[i][j]==10) {
					System.out.print(label[10]);
				}
				
				
				else {
					System.out.println("Main display() error");
				}
				
			}//for j loop
			System.out.println();
		} //for i loop
		
		if(copyrealqty<0) {
			win=true;
		}
		
		if(fail==true) {
			gameover();
		}
		if(win==true) {
			gamewin();
		}
		
	}
	
	public void driver() {
		
		displayGame();
		
		Scanner sc = new Scanner(System.in);
		String input1=null;
		String input2=null;
		int x, y;
		System.out.print("輸入橫軸(x)座標:");
		input1=sc.next();
		System.out.print("輸入縱軸(y)座標:");
		input2=sc.next();
		
		x= Integer.parseInt(input1);
		y= Integer.parseInt(input2);
		
		if(coveredmap[y][x]==10) {
			
			coveredmap[y][x]=0;
			copyqty++;
			if(map[y][x]==9) {
				copyrealqty++;
			}
			
		}else {

			System.out.print("揭示按1, 標記按2:");
			String option = sc.next();
			
			
			if(option.equals("1")) {
				coveredmap[y][x]=map[y][x];
				if(map[y][x]==0) {
					coveredmap[y][x]=11;
				}
			}else if(option.equals("2")) {
				coveredmap[y][x]=10;
				copyqty--;
				if(map[y][x]==9) {
					copyrealqty--;
				}
			}else {
				System.out.println("輸入錯誤");
			}
			
		}
		
		driver();
		
	}
	
	public void makemines() {
		int[] xarr = randomNumber(new int[bombqty]);
		int[] yarr = randomNumber(new int[bombqty]);

		boolean status = checkM(xarr, yarr);
		if(status==true){
			
			//init int[] map, bombmap
			map = new int[total_size][total_size];
			bombmap = new int[total_size][total_size];

			//set bomb in map
			for(int i=0; i<xarr.length; i++) {
				int x= (xarr[i]-1);
				int y= (yarr[i]-1);
				map[y][x]=9;
			}
			
			
			int count=0;
			int size = total_size;
			//set number arround bomb
			for(int i=0; i<map.length; i++) {
				for(int j=0; j<map.length; j++) {
					count=0;
					
					if(map[i][j]!=9) {
						
						if(i>0 && j>0) {
							if(map[i-1][j-1]==9) {
								count++;
							}
						}
						if(i>0) {
							if(map[i-1][j]==9) {
								count++;
							}
						}
						
						if(i>0 && j<size-1) {
							if(map[i-1][j+1]==9) {
								count++;
							}
						}
						if(i<size-1 && j>0) {
							if(map[i+1][j-1]==9) {
								count++;
							}
						}
						if(i<size-1 && j<size-1) {
							if(map[i+1][j+1]==9) {
								count++;
							}
						}
						
						if(j>0) {
							if(map[i][j-1]==9) {
								count++;
							}
						}
						if(j<size-1) {
							if(map[i][j+1]==9) {
								count++;
							}
						}
						if(i<size-1) {
							if(map[i+1][j]==9) {
								count++;
							}
						}
						map[i][j]=count;
					}//if end
				}//for j end
			}//for i end
		}//if end
		
	}
	
	private int[] randomNumber(int[] mines) {
		int number=0;
		int[] m=mines;
		
		for(int i=0; i< bombqty; i++) {
			if(m[i]==0) {
				m[i]=(int) (Math.random()*total_size)+1;
			}
		}
		return m;
	}
	
	private boolean checkM(int[] mine1, int[] mine2) {
		boolean status=false;
		int[] m=mine1;
		int[] m2=mine2;
		
		for(int i=0; i<m.length; i++) {
			for(int j=0; j<i && i>0; j++) {
				
				if(m[i]==m[j] && m2[i]==m2[j]) {
					m[i]=0;
					randomNumber(m);
					checkM(m,m2);
				}else {
					status=true;
				}
			}
		}
		return status;
	}
	
	private void gameover() {
		
		System.out.println("GAME OVER");
		Scanner sc = new Scanner(System.in);
		System.out.println("要重新開始嗎? (按1)\n"+
							"結束按0");
		String input = sc.next();
		if(input.equals("1")) {
			init();
			System.out.println("NEW GAME START!");
			makemines();
			driver();
		}else if(input.equals("0")) {
			System.out.println("謝謝遊玩");
			System.exit(0);
		}else {
			System.out.println("輸入有誤");
		}
		
	}
	
	private void gamewin() {
		System.out.println("YOU WIN");
		Scanner sc = new Scanner(System.in);
		System.out.println("要重新開始嗎? (按1)\n"+
							"結束按0");
		String input = sc.next();
		if(input.equals("1")) {
			init();
			makemines();
			driver();
		}else if(input.equals("0")) {
			System.out.println("謝謝遊玩");
			System.exit(0);
		}else {
			System.out.println("輸入有誤");
		}
		
	}
	
	 
	public static void main(String[] args) {


		Main main = new Main();
		main.makemines();
		main.driver();
		 
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
 
 
 
 
 

}