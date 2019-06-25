public class FloorsArrayList implements DynamicSet {
	FloorsArrayLink firstLink;
	FloorsArrayLink lastLink;
	int size;
	int arrMaxSize;
	int maxSize;
	
    public FloorsArrayList(int N){
    	firstLink= new FloorsArrayLink(Double.NEGATIVE_INFINITY,N+1);//check about N
    	lastLink= new FloorsArrayLink(Double.POSITIVE_INFINITY,N+1);
    	firstLink.RN=lastLink;
    	lastLink.LN=firstLink;
    	size=0;
    	arrMaxSize=0;
    	maxSize=N;
    	
    	//ADDED CODE
    	FloorsArrayLink[] frontArr=firstLink.frontArr;
    	FloorsArrayLink[] backArr=lastLink.backArr;
    	for(int i=0;i<N;i++) {
    		frontArr[i]=lastLink;
    		backArr[i]=firstLink;
    	}
    }

    @Override
    public int getSize(){
        return size;
    }

    @Override
    public void insert(double key, int arrSize) {
    	if (size==maxSize) 
    		return; 
    	//we don't want to insert values once the list is at its max capacity.
        	this.size=size+1;
        	if(arrSize>this.arrMaxSize) {
        		arrMaxSize=arrSize;
        	}//updating the new array maximal size in case we inserted an array with a bigger size array.

    	FloorsArrayLink prevLink =search(key);
    	//using the function "search" to determine where the link should be inserted (it gives us its future left neighbor-LN).
    	
    	FloorsArrayLink newLink = new FloorsArrayLink(key, arrSize); //creating the new link. 
    	(prevLink.RN).LN=newLink;
    	newLink.RN=prevLink.RN;
    	newLink.LN=prevLink;
    	prevLink.RN=newLink;
    	
    	//connecting the link to the proper neighbors.
    	// remember to change indexes for getnext setnext functions
    	int j=1;
    	while(prevLink!=null & j<=arrSize) {//maybe change
    		if(prevLink.getArrSize()>=j) {//we only want to update arrays that have spots "higher" then what we updated before,
										//the index j holds the highest point reached so far.
    			int min=Math.min(arrSize, prevLink.getArrSize());
    			while(j<=min) {
    				prevLink.setNext(j,newLink);
    				newLink.setPrev(j,prevLink);
    				j++;
    			}
    		}
    		prevLink=prevLink.getPrev(prevLink.getArrSize());
    	}
    	int i=1;
    	FloorsArrayLink nextLink=newLink.RN;
    	while(nextLink!=null & i<=arrSize) { //until we reach the right end of the list.
    		if(nextLink.getArrSize()>=i) {//we only want to update arrays that have spots "higher" then what we updated before,
    									//the index i holds the highest point reached so far.
    			int min =Math.min(arrSize, nextLink.getArrSize());
    			while( i<=min) {
    				nextLink.setPrev(i,newLink); 
    				newLink.setNext(i, nextLink);
    				i++;
    			}
    		}
    		nextLink=nextLink.getNext(nextLink.getArrSize());
    	}
    }

    @Override
    public void remove(FloorsArrayLink toRemove) {
    	this.size=size-1;
    	if(this.size==0) {
    		arrMaxSize=0;
    	}
    	else {
    		int k =toRemove.getArrSize();
        	if (k==arrMaxSize) {//updating the new maximal array.
        		k=k-1;
        		while(toRemove.backArr[k]==this.firstLink & toRemove.frontArr[k]==this.lastLink) {
        			k=k-1;//we want to locate the next cell that doesn't point to the last or first link.
        		}
        		arrMaxSize=Math.min(toRemove.backArr[k].getArrSize(), toRemove.frontArr[k].getArrSize());
        	}//cells are only pointing to links with higher or equal arrays, so if one is bigger than the other at this point, than it is the first or the last.
    	}
    	FloorsArrayLink prev =toRemove.LN; //updating the neighbors field
    	FloorsArrayLink next =toRemove.RN;
    	prev.RN = next;
    	next.LN =prev;
    	for(int i=1;i<=toRemove.getArrSize();i++) {
    		FloorsArrayLink left = toRemove.getPrev(i);
        	FloorsArrayLink right = toRemove.getNext(i);
        	left.setNext(i,right);
        	right.setPrev(i,left);
    	}	
    }
    
    @Override
    public FloorsArrayLink lookup(double key) {
    	FloorsArrayLink result =(search(key));
    	if (result.getKey()==key) return result;
    		
    	else return null;
    }

    @Override
    public double successor(FloorsArrayLink link) {
    	//System.out.println(link.GetRN().getKey());
    	if (link.RN==lastLink) {
    		return Double.NEGATIVE_INFINITY;
    	}
    	return link.RN.key;
    }

    @Override
    public double predecessor(FloorsArrayLink link) {
    	if (link.LN==firstLink) {
    		return Double.POSITIVE_INFINITY;
    	}
    	return link.LN.key; 

    }

    @Override
    public double minimum() {
        return firstLink.RN.key;
    }

    @Override
    public double maximum() {
       return lastLink.LN.key;
    }
    
    //side assistant function
    private FloorsArrayLink search(double key) {
    	FloorsArrayLink[] currentArr=firstLink.frontArr;
    	FloorsArrayLink currentLink=firstLink;
    	int i =arrMaxSize;
    	while (i>=0) {
    		while(currentArr[i].getKey()<=key) {
    			if (currentArr[i].getKey()==key) return currentArr[i];
    			currentLink=currentArr[i];
    			currentArr=currentLink.frontArr;
    		}
    		i=i-1;
    	}
    	return currentLink;
    }    
}