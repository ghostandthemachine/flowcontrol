package trie;


import java.io.*;
import java.util.*;

/**
 *  The only thing that can't be stored in a
 *  Trie safely is another Trie - we use
 *  instanceof to determine if a Trie is
 *  terminated or not
 */
public final class Trie {

    static final int BRANCHES = 26;
    //  Information about the location of this Trie in the
    //  trie tree ;)
    int position;
    Trie previous;
    //  For when a string ends on this trie
    TrieEntry at;
    //  The contents of this trie
    Object entries[];

    /**
     * Creates a new trie with no entries
     */
    public Trie() {
        entries = new Object[BRANCHES];
        at = null;
        position = 0;
        previous = null;
    }

    //  Internal constructor
    private Trie(Trie parent) {
        this();

        previous = parent;
        position = previous.getPosition() + 1;
    }

    String spaces() {
        StringBuffer s = new StringBuffer();

        for (int i = 0; i < position; i++) {
            s.append(" ");


        }
        return (s.toString());
    }

    /**
     *  Counts the number of linked objects off this trie
     */
    public int length() {
        int count = 0;

        if (at != null) {
            count++;


        }
        for (int i = 0; i < BRANCHES; i++) {
            if (entries[i] != null) {
                if (entries[i] instanceof TrieEntry) {
                    count++;
                    
                } else {
                    count += ((Trie) entries[i]).length();
                    
                }
            }
        }

        return (count);
    }

    /**
     *  Removes the object with the given key from the
     *  trie
     */
    public void remove(String key) throws NonUniqueKeyException, java.util.NoSuchElementException, BadKeyException {
        if (key.length() == position) {		//  item is on the 'at' for this trie
            if (at != null) {
                at = null;
                return;
            } else {
                throw new NonUniqueKeyException(key);

            }
        } else {
            int i = index(key.charAt(position));

            if (entries[i] != null) {
                if (entries[i] instanceof Trie && (((Trie) entries[i]).previous == this)) {
                    ((Trie) entries[i]).remove(key);
                    int a = ((Trie) entries[i]).length();
                    if (a == 1) {		//  lower trie is now not required
                        TrieEntry t = ((Trie) entries[i]).firstElement();
                        if (t != null) {
                            //System.out.println( spaces() + "removing trie with item '" + t.getKey() + "' swapup..." );
                            entries[i] = t;
                        } else {
                            entries[i] = null;
                            
                        }
                    }
                } else {
                    entries[i] = null;
                    return;
                }
            } else {
                throw new java.util.NoSuchElementException(key);
                
            }
        }
    }

    /**
     * Inserts the provided object into the trie
     * with the given key.
     */
    public void insert(String key, Object item) throws NonUniqueKeyException, BadKeyException {
        if (key.length() == position) {		//  this item needs to be inserted _on_ this trie
            if (at != null) {
                throw new NonUniqueKeyException(key);
                
            }
            at = new TrieEntry(key, item);
        } else {
            int i = index(key.charAt(position));

            if (entries[i] != null) {		//  already an item here
                if (entries[i] instanceof Trie && (((Trie) entries[i]).previous == this)) {
                    //System.out.println( spaces() + "passdown insert" );
                    ((Trie) entries[i]).insert(key, item);
                    return;
                } else {		//  already something here - create a new
                    //  trie, insert this something, and insert
                    //  the new something
                    if (((TrieEntry) entries[i]).getKey().equals(key)) {
                        throw new NonUniqueKeyException(key);

                        //System.out.println( spaces() + "item already in place" );
                        
                    }
                    Trie down = new Trie(this);

                    //System.out.println( spaces() + "re-inserting " +
                    //	((TrieEntry)entries[i]).getKey() );

                    down.insert(((TrieEntry) entries[i]).getKey(),
                            ((TrieEntry) entries[i]).getEntry());

                    //System.out.println( spaces() + "inserting " + key );
                    down.insert(key, item);

                    //System.out.println( spaces() + "updating the new trie for insertion" );
                    entries[i] = down;
                }
            } else {		//  the space is empty - simply add it in
                entries[i] = new TrieEntry(key, item);
                //System.out.println( spaces() + "straight insert of " + key );
            }
        }
    }

    /**
     * searchExact does *not* return a Trie for 'multiple matches',
     * but will return null in that case.
     */
    public Object searchExact(String key) {
        if (key.length() == position) {		//  check at
            if (at != null) {		//  this must be it
                return (at.getEntry());
            } else {		//  multiple matches in this case
                return (null);
            }
        }
        int i = 0;

        i = negIndex(key.charAt(position));
        if (i == -1) {		//  act as if this was the end of the
            //  string - return the at, if there is one
            if (at != null) {
                return (at.getEntry());
                
            } else {
                return (null);
                
            }
        }

        if (entries[i] != null) {
            if (entries[i] instanceof Trie) {		//  pass it along the chain
                return (((Trie) entries[i]).searchExact(key));
            } else {		//  found it
                //  some interesting behaviour here - if
                //  the next character is an invalid, there
                //  isn't much chance the key is correct - we'll
                //  treat it as if it was the end of the string (as
                //  per the position == key.length() code above
                //
                //  this little 'hack' is not required for a normal
                //  search, which will find the element as soon as
                //  it has a non-conflicting match
                if ((position + 1) < key.length()) {
                    if (negIndex(key.charAt(position + 1)) == -1 && key.substring(0, position + 1).equalsIgnoreCase(((TrieEntry) entries[i]).getKey())) {
                        return (((TrieEntry) entries[i]).getEntry());
                        
                    }
                }

                if (key.equalsIgnoreCase(((TrieEntry) entries[i]).getKey())) {
                    return (((TrieEntry) entries[i]).getEntry());
                    
                } else {
                    return (null);
                    
                }
            }
        } else {
            return (null);
            
        }
    }

    /**
     *  Returns a Trie of all the elements starting with
     *  'key', or an Object which is a single exact match
     */
    public Object getTrieFor(String key) {
        if (key.length() == position) {
            return (this);
        }
        int i = 0;

        i = negIndex(key.charAt(position));
        if (i == -1) {
            return (this);


        }
        if (entries[i] != null) {
            if (entries[i] instanceof Trie) {		//  pass it along the chain
                return (((Trie) entries[i]).getTrieFor(key));
            } else {
                if (((TrieEntry) entries[i]).getKey().startsWith(key.toLowerCase())) {
                    return (((TrieEntry) entries[i]).getEntry());
                    
                }
            }
        }

        return (null);
    }

    /**
     *  Returns a trie for multiple matches,
     *  else returns null for no object matched,
     *  or returns the object matched.  could
     *  easily be optimised
     */
    public Object search(String key) {
        if (key.length() == position) {		//  check at
            if (at != null) {		//  this must be it
                return (at.getEntry());
            } else {		//  multiple matches in this case
                return (this);
            }
        }
        int i = 0;

        i = negIndex(key.charAt(position));
        if (i == -1) {		//  act as if this was the end of the
            //  string - return the at, if there is one
            if (at != null) {
                return (at.getEntry());
                
            } else {
                return (null);
                
            }
        }

        if (entries[i] != null) {
            if (entries[i] instanceof Trie) {		//  pass it along the chain
                return (((Trie) entries[i]).search(key));
            } else {		//  found it (this check may not be required?)
                if (((TrieEntry) entries[i]).getKey().startsWith(key.toLowerCase())) {
                    return (((TrieEntry) entries[i]).getEntry());
                    
                } else {
                    return (null);
                    
                }
            }
        } else {
            return (null);
            
        }
    }

    /**
     *  From a number between 0 and 25 returns
     *  the alpha equivalent (between a and z)
     *
     * @see index
     */
    static char letter(int i) {
        return ((char) (i + 'a'));
    }

    /**
     *  Converts a letter between a and z to a number
     *  between 0 and 25
     *
     * @see letter
     */
    public static final int index(char c) throws BadKeyException {
        int r = negIndex(c);
        if (r == -1) {
            throw new BadKeyException(c);


        }
        return (r);
    }

    /**
     *  Returns -1 on an error as opposed to throwing an
     *  exceptions (exceptions are slow and ugly)
     */
    public static final int negIndex(char c) {
        int r = java.lang.Character.toLowerCase(c) - 'a';
        if (r < 0 || r > 25) {
            return (-1);
            
        } else {
            return (r);
            
        }
    }

    /**
     *  Returns the first element in a trie -
     *  is generally only used when an element
     *  is erased from the trie and a trie delete
     *  and swapup is required.
     */
    TrieEntry firstElement() {
        if (at != null) {
            return at;


        }
        for (int i = 0; i < BRANCHES; i++) {
            if (entries[i] != null && !(entries[i] instanceof Trie && (((Trie) entries[i]).previous == this))) {
                return ((TrieEntry) entries[i]);

                
            }

        }
        return (null);
    }

    /**
     *  Returns the character offset that this
     *  trie is off the main trie
     */
    int getPosition() {
        return (position);
    }

    /**
     *  Returns an alphabetically sorted list of
     *  the elements in this trie.  This routine
     *  is rather inefficient.
     */
    public Enumeration elements() {
        Vector v = new Vector(30, 20);

        elements(v);

        return (v.elements());
    }

    /**
     *  Returns an alphabetically sorted list of
     *  the elements in this trie
     */
    public LinkedList getSortedList() {
        LinkedList ll = new LinkedList();

        listElements(ll);

        return (ll);
    }

    /**
     *  Recursive function to append elements
     *  to a linked list
     */
    protected void listElements(LinkedList ll) {
        if (at != null) {
            ll.addLast(at.getEntry());
            //append isn't suuported so this is a fix
//			ll.append( at.getEntry() );


        }
        for (int i = 0; i < BRANCHES; i++) {
            if (entries[i] != null) {
                if (entries[i] instanceof Trie && (((Trie) entries[i]).previous == this)) {
                    ((Trie) entries[i]).listElements(ll);
                } else {
                    ll.addLast(((TrieEntry) entries[i]).getEntry());
//					ll.append( ((TrieEntry)entries[i]).getEntry() );
                }
            }
        }
    }

    /**
     *  Recursive function to append elements
     *  to a linked list
     */
    protected void elements(Vector v) {
        if (at != null) {
            v.addElement(at.getEntry());


        }
        for (int i = 0; i < BRANCHES; i++) {
            if (entries[i] != null) {
                if (entries[i] instanceof Trie && (((Trie) entries[i]).previous == this)) {
                    ((Trie) entries[i]).elements(v);
                } else {
                    v.addElement(((TrieEntry) entries[i]).getEntry());
                    
                }
            }
        }
    }

    /**
     *  This is a simple little test routine
     *  included so this class can be run as
     *  an application to test its cababilities
     */
    public static void main(String args[]) {
        Trie main = new Trie();
        String entry;
        int count = 0;

        do {
            System.out.println("\nTrie contains " + main.length() + " elements");
            entry = input("dump,quit,add,search,delete,avg,num: ");
            if (entry.length() == 0) {
            } else if (entry.equals("avg")) {
                System.out.println("\nAverage clash: " + Float.toString(main.averageClash()) + "\n");
            } else if (entry.equals("num")) {
                System.out.println("\nTotal number of tries: " + main.numberTries() + "\n");
            } else if (entry.equals("dump")) {
                System.out.println("\nDUMPING:\n--------");
                main.dump();
            } else if (entry.startsWith("search ")) {
                entry = entry.substring(7);
                System.out.println("Searching for " + entry + "...");
                Object r = null;
                r = main.search(entry);
                if (r == null) {
                    System.out.println("Not found");
                } else if (r instanceof Trie) {
                    System.out.println("Multiple matches:");
                    ((Trie) r).quickDump();
                } else {
                    System.out.println("Found. Value is " + r.toString());
                }
            } else if (entry.equals("quit")) {
            } else if (entry.startsWith("delete ")) {
                entry = entry.substring(7);
                System.out.println("Deleting " + entry + "...");
                try {
                    main.remove(entry);
                } catch (java.util.NoSuchElementException e) {
                    System.out.println(e.toString());
                } catch (NonUniqueKeyException e) {
                    System.out.println(e.toString());
                } catch (BadKeyException e) {
                    System.out.println(e.toString());
                }
            } else if (entry.startsWith("add ")) {
                entry = entry.substring(4);
                System.out.println("Inserting " + entry + "...");
                try {
                    main.insert(entry, new java.lang.Integer(count++));
                } catch (BadKeyException e) {
                    System.out.println(e.toString());
                } catch (NonUniqueKeyException e) {
                    System.out.println(e.toString());
                }
            } else {
                System.out.println("Unknown command '" + entry + "'");
            }
        } while (!entry.equals("quit"));
    }

    public static String input(String prompt) {
        StringBuffer buildInput = new StringBuffer();
        char b = ' ';

        System.out.print(prompt);
        System.out.flush();

        do {
            try {
                b = (char) System.in.read();
            } catch (java.io.IOException e) {
                //  generally an IOException here means that
                //  the player in question has disconnected
                System.out.println(e.toString());
                System.exit(1);
            }
            if (b != '\n' && b != 0) {
                buildInput.append(b);

            }
        } while (b != '\n' && b != 0);

        return (new String(buildInput));
    }

    public String toString() {
        if (length() > 20) {
            return ("Multiple matches: <too many to list (>20)>");
            
        } else {
            return ("Multiple matches: " + contents(new StringBuffer()));
            
        }
    }

    public String contents() {
        return (contents(new StringBuffer()));
    }

    public String contents(StringBuffer b) {
        if (at != null) {
            b.append(at.getKey());
            b.append(" ");
        }

        for (int i = 0; i < BRANCHES; i++) {
            if (entries[i] != null) {
                if (entries[i] instanceof Trie && (((Trie) entries[i]).previous == this)) {
                    b.append(((Trie) entries[i]).contents());
                } else {
                    b.append(((TrieEntry) entries[i]).getKey());
                    b.append(" ");
                }
            }
        }
        return (b.toString());
    }

    void quickDump() {
        if (at != null) {
            System.out.print(at.getKey() + " ");


        }
        for (int i = 0; i < BRANCHES; i++) {
            if (entries[i] != null) {
                if (entries[i] instanceof Trie && (((Trie) entries[i]).previous == this)) {
                    ((Trie) entries[i]).quickDump();
                } else {
                    System.out.print(((TrieEntry) entries[i]).getKey() + " ");
                    
                }
            }
        }
    }

    void dump() {
        String pre = spaces();
        if (at != null) {
            System.out.println(pre + at.getKey());


        }
        for (int i = 0; i < BRANCHES; i++) {
            if (entries[i] != null) {
                if (entries[i] instanceof Trie && (((Trie) entries[i]).previous == this)) {
                    System.out.println(pre + "[" + letter(i) + "]");
                    ((Trie) entries[i]).dump();
                } else {
                    System.out.println(pre + ((TrieEntry) entries[i]).getKey());
                    
                }
            }
        }
    }

    void averageClash(TrieAverage t) {
        if (at != null) {
            t.add(position);


        }
        for (int i = 0; i < BRANCHES; i++) {
            if (entries[i] != null) {
                if (entries[i] instanceof Trie && (((Trie) entries[i]).previous == this)) {
                    ((Trie) entries[i]).averageClash(t);
                } else {
                    t.add(position);
                    
                }
            }
        }
    }

    float averageClash() {
        TrieAverage t = new TrieAverage();
        averageClash(t);
        return (((float) t.average) / ((float) t.number));
    }

    int numberTries() {
        int accum = 1;

        for (int i = 0; i < BRANCHES; i++) {
            if (entries[i] != null) {
                if (entries[i] instanceof Trie && (((Trie) entries[i]).previous == this)) {
                    accum += ((Trie) entries[i]).numberTries();
                    
                }
            }
        }

        return (accum);
    }
}

class TrieAverage {

    int average;
    int number;

    public TrieAverage() {
    }

    public void add(int i) {
        average += i;
        number++;
    }
}

/**
 *  A private class which is used to
 *  internally mark off elements to
 *  items
 */
class TrieEntry implements Serializable {

    String key;
    Object entry;

    public TrieEntry() {
        key = "";
        entry = null;
    }

    public TrieEntry(String trigger, Object store) {
        key = trigger.toLowerCase();
        entry = store;
    }

    public String getKey() {
        return (key);
    }

    public Object getEntry() {
        return (entry);
    }
}
