import java.util.Arrays;

public class TestSort {



    public static void main(String[] args) {
        int[] x = { 1, 5, 8, 7, 6, 9, 12, 4, 165, 85, 145 }; // 定义初始数组
        //int[] x = {1, 4, 5, 6, 7, 8, 9, 12, 85, 145, 165 }; // 定义初始数组
        //int[] x = {165, 145, 85, 12, 9, 8, 7, 6, 5, 4, 1 }; // 定义初始数组

        //int[] x={89, -7, 999, -89, 7, 0, -888, (7), (-7)};
        //sort(x);

        quickSort(x, 0, x.length-1);
    }

    //原理：在一个数组中，从第一个元素开始循环比较，第一个元素与第二个元素相比较，
    // 例如是按升序排列，如果第一个元素大于第二个元素，则交换两个元素，
    // 然后比较第二个和第三个元素，在接下来是第三个和第四个······直到所有元素比较完，
    // 这样一来就完成了对数组的升序排列
    public static void sort(int[] arr){
        if(arr==null||arr.length<=0){
            return;
        }
        for (int i = 0; i < arr.length - 1; i++) { // 每一趟排序完成之后就将大的元素排到最后
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }

            }
            System.out.println(Arrays.toString(arr)); // 输出每一趟排序后的结果
        }
    }



    //快速排序的基本思想：
    //假设我们以升序为例，它的执行流程可以概括为，每一趟选择当前所有子序列中的一个关键字
    // (通常我们选择第一个)作为枢纽，将子序列中比枢纽小的移动到枢纽前面，
    // 比枢纽大的移动到枢纽的后边；
    // 当本趟所有子序列都被枢纽以上述规则划分完毕后会得到新的一组更短的子序列，
    // 我们将这个子序列作为下一趟划分的初始序列集。
    public static void quickSort(int[] arr,int left,int right){
        int temp;  //基准
        int low = left;   //最左边的元素
        int high = right;  //最右边的元素
        if(left<right){
            //我们将这个数组中的第一个数赋值给temp。
            temp = arr[left];   //temp想当于我们的基准，数列的第一个数
            while(low!=high){   //我们假定，当low=high的时候退出该循环
                //开始的时候，我们从最右边开始找元素，当我们找的元素的值大于等于我们的基准，
                // 那么我们将它向左移，直到这个元素的值小于基准为止。
                while(high>low && arr[high]>=temp){
                    --high;
                }
                //此时arr[high]已经小于temp，这个时候我们需要进行一个判断，看看是否还是符合low<high，
                // 如果符合的话，我们将arr[high]这个值，直接赋值到左边(数组的第一个值)
                //并且让我们左边的l指向下一个位置。
                if(low<high){
                    arr[low] = arr[high];
                    ++low;
                }
                //在右边换过来之后，我们需要交叉得变换，这个时候我们从左边开始比较
                //前提还是low<high，只不过由于我们在左边开始，
                // 所以我们需要判断arr[low]如果小于temp的值，那么我们应该继续向右移，
                // 直到大于等于temp的值为止。
                while(low<high && arr[low]<temp){
                    ++low;
                }
                //此时arr[high]已经大于或者等于temp，这个时候我们需要进行一个判断，
                // 看看是否还是符合l<high，如果符合的话，我们将arr[high]这个值，
                // 直接赋值到右边(r所在的位置),并且让我们右边的r指向下一个位置。
                if(low<high){
                    arr[high] = arr[low];
                    --high;
                }

            }
            //在执行完上面的循环之后，我们除了基准的位置的空的外，
            // 基准左边的都小于它，基准右边的都大于它，形成了一个相对有序的序列，
            //最终我们把之前赋值的temp加入到arr[low]中(因为这个时候r=low)
            arr[low] = temp;
            System.out.println(Arrays.toString(arr));
            //执行完上面的依次循环之后，我们需要对子序列进行在依次的快速排序，执行过程与上面的相同。
            quickSort(arr, left, low-1);
            quickSort(arr, low+1, right);
        }
    }
}
