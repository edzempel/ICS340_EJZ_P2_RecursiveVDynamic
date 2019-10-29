package edu.metrostate.by8477ks.atm;

// Java implementation to count ways
// to sum up to a given value N

class DynamicATM
{
    int arr[];

    public DynamicATM(){
        super();
    }

    public DynamicATM(int[] arr) {
        this.arr = arr;
    }

    public void setArr(int[] arr) {
        this.arr = arr;
    }

    // method to count the total number
    // of ways to sum up to 'N'
    int countWays(int n)
    {
        int count[] = new int[n + 1];

        // base case
        count[0] = 1;

        // count ways for all values up
        // to 'n' and store the result
        for (int i = 1; i <= n; i++)
            for (int j = 0; j < arr.length; j++)

                // if i >= arr[j] then
                // accumulate count for value 'i' as
                // ways to form value 'i-arr[j]'
                if (i >= arr[j])
                    count[i] += count[i - arr[j]];

        // required number of ways
        return count[n];

    }

    // Driver code
    public static void main(String[] args)
    {
        DynamicATM atmUSA = new DynamicATM(new int[]{1,5,10,20,50,100});
        for(int n = 0; n < 200; n++) {
            System.out.println(String.format("Total number of ways to make %d = %d", n,
                    atmUSA.countWays(n)));
        }
    }
}
