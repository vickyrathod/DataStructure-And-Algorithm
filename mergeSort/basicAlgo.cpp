#include <iostream>
using namespace std;

void merge(int arr[], int l, int m, int r){
    int n1 = m - l + 1;
    int n2 = r - m;
    
    int left[n1];
    int right[n2];
    
    for(int i = 0; i < n1; i ++)
        left[i] = arr[l + i];
    for(int i = 0; i < n2; i ++)
        right[i] = arr[m + i + 1];
    
    int i = 0, j = 0, c = l;
    while(i < n1 && j < n2){
        if(left[i] < right[j]){
            arr[c] = left[i];
            i ++;
            c ++;
        } else {
            arr[c] = right[j];
            j ++;
            c ++;
        }
    }
    
    if(i == n1){
        for(;j < n2; j ++){
            arr[c] = right[j];
            c ++;
        }
    }
    
    if(j == n2){
        for(; i < n1; i ++){
            arr[c] = left[i];
            c ++;
        }
    }
}

void mergeSort(int l , int h, int arr[]){
    if(l == h){
        return;
    }
    
    int m = l + (h - l) / 2;
    
    mergeSort(l , m, arr);
    mergeSort(m + 1 , h, arr);
    
    merge(arr, l, m, h);
}

int main()  {
    int n;
    cin >> n;
    int arr[n];
    for(int i = 0; i < n; i++)
        cin >> arr[i];
    
    mergeSort(0, n - 1, arr);
    cout << endl;
    for(int i = 0; i < n; i ++){
        cout << arr[i] << " ";
    }
    return 0;
}
