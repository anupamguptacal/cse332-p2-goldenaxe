# Project 2 (uMessage) Write-Up #
--------

## Project Enjoyment ##
- How Was Your Partnership?
  <pre>TODO</pre>
  
- What was your favorite part of the project?
  <pre>TODO</pre>

- What was your least favorite part of the project?
  <pre>TODO</pre>

- How could the project be improved?
  <pre>TODO</pre>

- Did you enjoy the project?
  <pre>TODO</pre>
    
-----

## Experiments ##
Throughout p1 and p2, you have written (or used) several distinct implementations of the Dictionary interface:
 - HashTrieMap 
 - MoveToFrontList
 - BinarySearchTree
 - AVLTree
 - ChainingHashTable
 
 In this Write-Up, you will compare various aspects of these data structures.  This will take a significant amount of
 time, and you should not leave it to the last minute.  For each experiment, we expect you to:
 - Explain how you constructed the inputs to make your conclusions
 - Explain why your data supports your conclusions
 - Explain your methodology (e.g., if we wanted to re-run your experiment, we would be able to)
 - Include the inputs themselves in the experiments folder
 - Include your data either directly in the write-up or in the experiments folder
 - If you think it helps your explanation, you can include graphs of the outputs (we recommend that you do this for some of them)
 - We recommend that you keep your "N" (as in "N-gram") constant throughout these experiments. (N = 2 and N = 3 are reasonable.) 
 
### BST vs. AVLTree ###
Construct input files to NGramTester of your choosing to demonstrate that an AVL Tree is asymptotically better
than a Binary Search Tree.
<pre>TODO</pre>

### BST Worst Case vs. BST Best Case ###
We know that the worst case for a BST insertion is O(n) and the best case is O(lg n).  Construct input
files of your choosing that demonstrate these best and worst cases for a large n.  How big is the difference?
Is it surprising?
<pre>TODO</pre>
 
### ChainingHashTable ###
Your ChainingHashTable should take as an argument to its constructor the type of "chains" it uses.  Determine
which type of chain is (on average) best: an MTFList, a BST, or an AVL Tree.  Explain your intuition on why
the answer you got makes sense (or doesn't!). 
<pre>TODO</pre>
 
### Hash Functions ###
Write a new hash function (it doesn't have to be any good, but remember to include the code in your repository).
Compare the runtime of your ChainingHashTable when the hash function is varied.  How big of a difference can the
hash function make?  (You should keep all other inputs (e.g., the chain type) constant.)
<pre>TODO</pre>

### General Purpose Dictionary ###
Compare all of the dictionaries (on their best settings, as determined above) on several large input files.  Is
there a clear winner?  Why or why not?  Is the winner surprising to you?
<pre>TODO</pre>

### General Sorts ###
You have several general purpose sorts (InsertionSort, HeapSort, QuickSort).  We would like you to compare these
sorts using *step counting*. That is, for all other experiments, you likely compared the time it took for the various
things to run, but for this one, we want you to (1) choose a definition of step, (2) modify the sorting algorithms to
calculate the number of steps, and (3) compare the results.  In this case, there is a "good" definition of step, and
there are many bad ones.  We expect you to justify your choice.
<pre>TODO</pre>

### Top K Sort ###
TopKSort should theoretically be better for small values of k.  Determine (using timing or step-counting--your choice)
which n (input size) and k (number of elements sorted) makes TopKSort worthwhile over your best sort from the previous
experiment. 
<pre>TODO</pre>

### uMessage ###
Use uMessage to test out your implementations.  Using N=3, uMessage should take less than a minute to load using
your best algorithms and data structures on a reasonable machine.

 -  How are the suggestions uMessage gives with the default corpus? (here, we mean spoken.corpus or irc.corpus, not eggs.txt)
    <pre>TODO</pre>

 - Now, switch uMessage to use a corpus of YOUR OWN text. To do this, you will need a corpus. 
   You can use anything you like (Facebook, google talk, e-mails, etc.)  We provide
   instructions and a script to format Facebook data correctly as we expect it will be the most common
   choice.  If you are having problems getting data, please come to office hours and ask for help.
   Alternatively, you can concatenate a bunch of English papers you've written together to get a corpus
   of your writing.  PLEASE DO NOT INCLUDE "me.txt" IN YOUR REPOSITORY.  WE DO NOT WANT YOUR PRIVATE CONVERSATIONS.
     * Follow these instructions to get your Facebook data: https://www.facebook.com/help/212802592074644
     * Run the ParseFBMessages program in the main package.
     * Use the output file "me.txt" as the corpus for uMessage.
 
 - How are the suggestions uMessage gives wth the new corpus?
   <pre>TODO</pre>


-----

## Above and Beyond ##
-   Did you do any Above and Beyond?  Describe exactly what you implemented.
 <pre>TODO</pre>