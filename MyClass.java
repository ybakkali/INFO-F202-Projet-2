
public class MyClass {

	public static void main(String[] args) {
		
		Integer intArray[] = {10,15,30,40,15,1,2,3,4,5,100,200,300,19,500,16};
		String  strArray[] = {"ALGO","LDP","MATHS","PHYSIQUE","AMI","OS","FDO","ALGO"};
		
		System.out.println(Duplicate.<Integer>isDuplicate(intArray,4,4)) ;
		System.out.println(Duplicate.<String>isDuplicate(strArray,0,2)) ;
		//System.out.println(Duplicate.<Integer>isDuplicate(intArray,3,4)) ;
		//System.out.println(Duplicate.<String>isDuplicate(strArray,4,4)) ;
	}
}
class findDuplicate<T> implements Runnable {
	
	private T[] array ;
	private int i ;
	private int end ; 
	private Find find ;
	private int index ;
	private int threadNumber ;
	
	public findDuplicate(int threadNumber ,T[] array , int begin , int end , Find find , int index ) {
		
		this.array = array ;
		this.i = begin ;
		this.end = end ;
		this.index = index ;
		this.find = find ;
		this.threadNumber = threadNumber ;
		
	}
	public void requestStop() {
		find.setStat();
	}
	public void run() {
		System.out.printf("Thread number %d :\n 	Begin at : %d / end at : %d \n",threadNumber,i,end-1) ;
		while ( i < end  ) {
			System.out.printf("Thread number %d (Index %d : %s)\n",threadNumber,i,find.getStat()) ;
			if ( array[i] == array[index] && i != index ) {	
				System.out.println("Thread number "+ threadNumber +" find it.") ;
				System.out.println("Index " + i + " : " + array[i]) ;
				requestStop() ;
			}
				
			if (find.getStat()) {
				System.out.printf("Thread number %d stop at index %d .\n",threadNumber,i) ;
				break ;
			}
			i++ ;
		}
	}
} 

class Duplicate<T> {

	public Duplicate() {} 
	
	public static <T> boolean isDuplicate(T[] array , int index , int K ) {
		
		Thread list[] = new Thread[K] ;
		int threadNumber =  array.length / K ;
		int sizeOfLastArray = array.length % K ;
		Find find = new Find() ;
		int j = 0 ;
		for (int i = 0 ; i < K ; ++i ) {
			
			if (i+1 == K) {
				j = sizeOfLastArray ;
			}
			list[i] = new Thread (new findDuplicate<T>(i+1,array , i*threadNumber , (i*threadNumber) + threadNumber + j, find , index))  ;
			list[i].start();
		}
		for (int x = 0 ; x < K ; ++x ) {
			try {
				list[x].join();			
			} catch (Exception e) {}
		}		
		return find.getStat() ;
	}
}

class Find {
	private boolean find ;
	
	public Find() {
		find = false ;
	}
	public boolean getStat() {
		return find ;
	}
	public void setStat() {
		find = true ;
	}
}
