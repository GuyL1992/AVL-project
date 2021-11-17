import java.util.Stack;

/**
 *
 * AVLTree
 *
 * An implementation of a׳� AVL Tree with
 * distinct integer keys and info.
 *
 */

public class AVLTree {
	
	 public static IAVLNode root = null;
	
	
	

  /**
   * public boolean empty()
   *
   * Returns true if and only if the tree is empty.
   *
   */
  public boolean empty() {
	  if (root==null) {
		  return true;
	  }
      return false; 
  }

 /**
   * public String search(int k)
   *
   * Returns the info of an item with key k if it exists in the tree.
   * otherwise, returns null.
   */
  public String search(int k)
  {
	  if (root == null) {
		  return null;
	  }else {
		  return (recSearch(root,k));
	  }
		  
  }
  
  public String recSearch (IAVLNode node,int k) {
	  if (!node.isRealNode()) {
		  return null;
	  }
	  if (node.getKey() == k) {
		  return node.getValue();
	  }else if(node.getKey() > k) {
		  return recSearch(node.getLeft(),k);
	  }else if(node.getKey() < k) {
			  return recSearch(node.getRight(),k);
	  }
	  return "";
  }

  /**
   * public int insert(int k, String i)
   *
   * Inserts an item with key k and info i to the AVL tree.
   * The tree must remain valid, i.e. keep its invariants.
   * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
   * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
   * Returns -1 if an item with key k already exists in the tree.
   */
   public int insert(int k, String i) {
	   
	   if (root == null) {
		   
		   root = new AVLNode(k,i);
		   return 0;
	   }
	   
	   
	   IAVLNode relevatParent = recGetInsertionParent(root,k);
	   
	   if (relevatParent == null) {
		   return -1;
	   }
	   Insertion(relevatParent,k,i);
	   
	   
	  return getMoves(relevatParent);
   }
   
   public IAVLNode recGetInsertionParent (IAVLNode node, int k) {
	   while (node.isRealNode()) {
		  if (node.getKey() == k) {
			  return null;
		  }else if(node.getKey() > k) {
			  node=node.getLeft();
		  }else if(node.getKey() < k) {
			  node=node.getRight();
		  }
		  
	  }
	   return node.getParent();
   }
   
   public void Insertion (IAVLNode node, int k, String i) {
	   IAVLNode newNode = new AVLNode(k,i);
	   
	   
	  /* if ( node.getRight() == null && node.getLeft() == null) {
		   node.setHeight(node.getHeight()+1);
	   }*/
	   
	   if (k < node.getKey()) {
		   
		   node.setLeft(newNode);
	   }
	   if (k > node.getKey()) {
		   
		   node.setRight(newNode);
	   }
	   //newNode.getLeft().setParent(newNode);
	   //newNode.getRight().setParent(newNode);
	   newNode.setParent(node);
	   
	   
   }
   
   public int getMoves(IAVLNode parent) {
	   int move = 0;
	   
	   
	   while (parent != null) { // to checkkkk!!!!!!!!
		   int deltaRight =parent.getHeight() - parent.getRight().getHeight() ;
		   int deltaLeft = parent.getHeight() -parent.getLeft().getHeight();
		   /*System.out.println(parent.getHeight());
		   System.out.println(deltaRight);
		   System.out.println(deltaLeft);*/

		   
		   
		   
		   // case 1
		   if (deltaRight == 1 && deltaLeft == 1)  {
			   return move; // no more balance steps are required 
		   }
		   // case 2
		   
		   if ((deltaRight == 1 && deltaLeft == 0 ) || (deltaRight == 0 && deltaLeft == 1 )){
			   parent.setHeight(parent.getHeight()+1); // promote the current node 
			   move++;
			   parent=parent.getParent(); // problem may moved up 
		   }
		   
		   // case 3A
		   
		   if ((deltaLeft == 0 && deltaRight == 2 )) {
			   
			   int sDeltaL = parent.getLeft().getHeight()-parent.getLeft().getLeft().getHeight(); // we need to observe the status of the leftSon
			   int sDeltaR = parent.getLeft().getHeight()-parent.getLeft().getRight().getHeight();
			   
			   if (sDeltaL == 1 && sDeltaR == 2) { // one rotation Right is needed 
				   rotationRight( parent , parent.getLeft()); 
				   move ++;
				   return move; // then problem solved 
				   
			   }else if (sDeltaL == 2 && sDeltaR == 1) { // double rotation are needed 
				   rotationLeft( parent.getLeft() , parent.getLeft().getRight());
				   rotationRight( parent , parent.getLeft());
				   parent.getParent().setHeight(parent.getParent().getHeight()+1);
				   move +=2;
				   return move; // then problem solved 
				   
			   }
		   	}
		   
		   //case 3B - up to symmetry 
		   
		   		else if((deltaLeft == 2 && deltaRight == 0 )) {
				   
				   int sDeltaL = parent.getRight().getHeight()-parent.getRight().getLeft().getHeight();
				   int sDeltaR = parent.getRight().getHeight()-parent.getRight().getRight().getHeight();
				   
				   if (sDeltaL == 2 && sDeltaR == 1) { // one rotation Left is needed 
					   rotationLeft( parent , parent.getRight());
					   move ++;
					   return move;
					   
				   }else if (sDeltaL == 1 && sDeltaR == 2) { // double rotation are needed
					   rotationRight( parent.getRight() , parent.getRight().getLeft());
					   rotationLeft( parent , parent.getRight());
					   parent.getParent().setHeight(parent.getParent().getHeight()+1);
					   move +=2;
					   return move;
				   }
			   
		   }
		    
	   }
	   
	   return move;
   }
   
   // להשלים את המקרה של רוטציה על השורש!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
   
   
   public static void rotationRight(IAVLNode father , IAVLNode son) {
	   father.setLeft(son.getRight());
	   son.getRight().setParent(father);
	   if (father.getParent() != null ) {
		   
	   if (father.getParent().getLeft() == father) {
		   father.getParent().setLeft(son);
		   
	   }
	   else {
		   father.getParent().setRight(son);
		   
	   }
	   
	   son.setParent(father.getParent());
	   
	   
	  }
	   
	   
	   else {
		   son.setParent(null);
		   root = son;
	   }
	   son.setRight(father);
	   father.setParent(son);
	   father.setHeight(father.getHeight()-1);

   }
   
   public static void rotationLeft(IAVLNode father , IAVLNode son) {
	   father.setRight(son.getLeft());
	   son.getLeft().setParent(father);
	   if (father.getParent() != null ) {
		   if (father.getParent().getRight() == father) {
		   father.getParent().setRight(son);
		   
	   }
	   else {
		   father.getParent().setLeft(son);
		   
	   }
		   
	   son.setParent(father.getParent());
		   
	  }
	   else {
		   son.setParent(null);
		   root = son;
	   }
	   son.setLeft(father);
	   father.setParent(son);
	   father.setHeight(father.getHeight()-1);

   }
   
   
   
	   
   
   

  /**
   * public int delete(int k)
   *
   * Deletes an item with key k from the binary tree, if it is there.
   * The tree must remain valid, i.e. keep its invariants.
   * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
   * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
   * Returns -1 if an item with key k was not found in the tree.
   */
   public int delete(int k)
   {
	   return 421;	// to be replaced by student code
   }

   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty.
    */
   public String min()
   {
	   if (root == null)
		   return null;
	   IAVLNode node = root;
	   while (node.getLeft().isRealNode() ) {
		   node=node.getLeft(); 
	   }
	   return node.getValue();
	   
	   
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty.
    */
   public String max()
   {
	   if (root == null)
		   return null;
	   IAVLNode node = root;
	   while (node.getRight().isRealNode() ) {
		   node=node.getRight(); 
	   }
	   return node.getValue();
   }

  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
  public int[] keysToArray()
  {
	  int [] empty = {};
	  if (root == null)
		  return empty;
	  
	  int size=0;
	  IAVLNode node = root;
	  Stack tempstack = new Stack();
	  Stack keystack = new Stack();

	  
	  while (true) {
		  if (node.isRealNode() ) {
			  tempstack.push(node);
			  node=node.getLeft();
		  }
		  else if (! tempstack.isEmpty()) {
			  node = (AVLTree.IAVLNode) tempstack.pop();
			  keystack.push(node.getKey());
			  size++;
			  node=node.getRight();
			  
		  }
		  else {
			  break;
		  }
		  
	  }
	  
	  int [] keys = new int [size];
	  
	  for (int i=keys.length-1; i>=0; i--) {
		  keys[i] = (int) keystack.pop();
	  }
	  return keys;
	  
  }

  /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   */
  public String[] infoToArray()
  {
        return new String[55]; // to be replaced by student code
  }

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    */
   public int size()
   {
	   int cnt=0;
	   if (root == null)
		   return 0;
		  IAVLNode node = root;
		  Stack tempstack = new Stack();

		  
		  while (true) {
			  if (node.isRealNode() ) {
				  tempstack.push(node);
				  node=node.getLeft();
			  }
			  else if (! tempstack.isEmpty()) {
				  node = (AVLTree.IAVLNode) tempstack.pop();
				  cnt++;
				  node=node.getRight();
				  
			  }
			  else {
				  break;
			  }
			  
		  }
		  return cnt;
   }
   
   /**
    * public int getRoot()
    *
    * Returns the root AVL node, or null if the tree is empty
    */
   public IAVLNode getRoot()
   {
	   return root;
   }
   
   /**
    * public AVLTree[] split(int x)
    *
    * splits the tree into 2 trees according to the key x. 
    * Returns an array [t1, t2] with two AVL trees. keys(t1) < x < keys(t2).
    * 
	* precondition: search(x) != null (i.e. you can also assume that the tree is not empty)
    * postcondition: none
    */   
   public AVLTree[] split(int x)
   {
	   return null; 
   }
   
   /**
    * public int join(IAVLNode x, AVLTree t)
    *
    * joins t and x with the tree. 	
    * Returns the complexity of the operation (|tree.rank - t.rank| + 1).
	*
	* precondition: keys(t) < x < keys() or keys(t) > x > keys(). t/tree might be empty (rank = -1).
    * postcondition: none
    */   
   public int join(IAVLNode x, AVLTree t)
   {
	   return -1;
   }

	/** 
	 * public interface IAVLNode
	 * ! Do not delete or modify this - otherwise all tests will fail !
	 */
	public interface IAVLNode{	
		public int getKey(); // Returns node's key (for virtual node return -1).
		public String getValue(); // Returns node's value [info], for virtual node returns null.
		public void setLeft(IAVLNode node); // Sets left child.
		public IAVLNode getLeft(); // Returns left child, if there is no left child returns null.
		public void setRight(IAVLNode node); // Sets right child.
		public IAVLNode getRight(); // Returns right child, if there is no right child return null.
		public void setParent(IAVLNode node); // Sets parent.
		public IAVLNode getParent(); // Returns the parent, if there is no parent return null.
		public boolean isRealNode(); // Returns True if this is a non-virtual AVL node.
    	public void setHeight(int height); // Sets the height of the node.
    	public int getHeight(); // Returns the height of the node (-1 for virtual nodes).
	}

   /** 
    * public class AVLNode
    *
    * If you wish to implement classes other than AVLTree
    * (for example AVLNode), do it in this file, not in another file. 
    * 
    * This class can and MUST be modified (It must implement IAVLNode).
    */
	
  public class VirtualAVLNode implements IAVLNode	 {
	  
	  IAVLNode parent;
	  
	  public VirtualAVLNode (AVLNode node) {
		  
		   this.parent = node;
		   
	   }
	  
	  
	  
	  
	  
	  public int getKey()
		{
			
			return -1; 
		}
		public String getValue()
		{
			return null; 
		}
		public void setLeft(IAVLNode node)
		{
		}
		public VirtualAVLNode getLeft()
		{
			return null; 
		}
		public void setRight(IAVLNode node)
		{
		}
		public VirtualAVLNode getRight()
		{
			return null; 
		}
		public void setParent(IAVLNode node)
		{
			this.parent =   node;
		}
		public IAVLNode getParent()
		{
			return this.parent; 
		}
		public boolean isRealNode()
		{
			return false; 
		}
	    public void setHeight(int height)
	    {
	    }
	    public int getHeight()
	    {
	      return -1; 
	    } 
	  
	  
	  
  }
	
  public class AVLNode implements IAVLNode{
	  
	   int key;
	   int height;
	   String info;
	   IAVLNode left;
	   IAVLNode right;
	   IAVLNode parent;
	   
	   public AVLNode (int k, String i) {
		   this.key=k;
		   this.info=i;
		   this.left  =  new VirtualAVLNode(this) ;
		   this.right = new VirtualAVLNode(this) ;
	   }
	   
	   
		public int getKey()
		{
			
			return this.key; 
		}
		public String getValue()
		{
			return this.info; 
		}
		public void setLeft(IAVLNode node)
		{
			this.left =  node;
		}
		public IAVLNode getLeft()
		{
			return this.left; 
		}
		public void setRight(IAVLNode node)
		{
			this.right =  node;
		}
		public IAVLNode getRight()
		{
			return this.right; 
		}
		public void setParent(IAVLNode node)
		{
			this.parent =  node;
		}
		public IAVLNode getParent()
		{
			return this.parent; 
		}
		public boolean isRealNode()
		{
			return true;
		}
	    public void setHeight(int height)
	    {
	      this.height = height;
	    }
	    public int getHeight()
	    {
	      return this.height; // to be replaced by student code
	    }
  }

}
  
