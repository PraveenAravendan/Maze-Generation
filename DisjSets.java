/**
 * This class is an implementation of a disjoint set data structure which implements
 * both union and find operations using Path compression
 * @author Mark Allen Weiss (Given in Textbook)
 */
public class DisjSets
{
    int[] set;

    public DisjSets(int numElements)
    {
        set = new int[numElements];

        for(int i = 0; i<set.length;i++)
        {
            set[i] = -1;
        }
    }

    public String toString()
    {
        String res = "";
        for(int i = 0; i < set.length; i++)
        {
            res += "\t"+set[i];
        }
        return res;
    }

    public void union(int root1, int root2){
        if(set[root2]<set[root1])
        {
            set[root2]+=set[root1];
            set[root1]=root2;
        }
        else
        {
            set[root1]+=set[root2];
            set[root2]=root1;
        }
    }

    public int find(int x)
    {
        if(set[x]<0)
        {
            return x;
        }
        else
            {
            return set[x]=find(set[x]);
        }
    }
}