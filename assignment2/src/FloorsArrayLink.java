public class FloorsArrayLink {
    double key;
    public int arrSize;
    public FloorsArrayLink [] backArr;
    public FloorsArrayLink [] frontArr;
    public FloorsArrayLink RN; //NEED TO CHECK* not sure if necessary
    public FloorsArrayLink LN; 
    

    public FloorsArrayLink(double key, int arrSize){
    	this.arrSize=arrSize;
    	backArr = new FloorsArrayLink[arrSize];
        frontArr = new FloorsArrayLink[arrSize];
        for(int i=0; i<arrSize; i++) {
        	backArr[i]=null;
        	frontArr[i]=null;
        }
        this.key=key;
        this.RN=null;
        this.LN=null;
    }

    public double getKey() {
        return key;
    }

    public FloorsArrayLink getNext(int i) {//NEED TO CHECK* if we should consider a case when i=0 for all actions below (then i-1=-1)
        if (i<=arrSize) { //entering the array only if i is a legal index 
        	//System.out.println("I: "+i);
        	return frontArr[i-1];
        }
        return null;
    }

    public FloorsArrayLink getPrev(int i) {
    	if (i<=arrSize) { //entering the array only if i is a legal index 
        	return backArr[i-1];
        }
        return null;
    }
 
    public void setNext(int i, FloorsArrayLink next) {
    	 if (i<=arrSize) { //entering the array only if i is a legal index 
    		 frontArr[i-1]=next;
    	 }
    }

    public void setPrev(int i, FloorsArrayLink prev) {
    	if (i<=backArr.length) { //entering the array only if i is a legal index 
    		backArr[i-1]=prev;
   	 }
    }

    public int getArrSize(){
        return this.arrSize;
    }
    
    public FloorsArrayLink GetRN() {
    	return RN;
    }
}

