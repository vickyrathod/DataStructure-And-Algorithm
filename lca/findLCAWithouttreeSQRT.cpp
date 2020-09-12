#include <bits/stdc++.h>
using namespace std;
#define MAXN 100000 
#define level 18 



void precomputeSparseMatrix(int node, int parent[][level]) 
{ 
    for (int i=1; i<level; i++) { 
        if (parent[node][i-1] != -1) 
                parent[node][i] = 
                    parent[parent[node][i-1]][i-1];
    }
} 

int lca(int u, int v, int parent[][level], int* depth) 
{ 
    if (depth[v] < depth[u]) 
        swap(u, v); 
  
    int diff = depth[v] - depth[u]; 
  
    // Step 1 of the pseudocode 
    for (int i=0; i<level; i++) 
        if ((diff>>i)&1) 
            v = parent[v][i]; 
  
    // now depth[u] == depth[v] 
    if (u == v) 
        return u; 
  
    // Step 2 of the pseudocode 
    for (int i=level-1; i>=0; i--) 
        if (parent[u][i] != parent[v][i]) 
        { 
            u = parent[u][i]; 
            v = parent[v][i]; 
        } 
  
    return parent[u][0]; 
}

int main() {
    int t;
    cin >> t;
    
    while(t -- ) {
        int n;
        cin >> n;
        int max = 1;
        vector <int> tree[MAXN];
            int depth[MAXN]; 
            int parent[MAXN][level];
            unordered_set<int> leaf;
        
            memset(parent, -1, sizeof(parent));
            depth[0] = 1;
            depth[1] = 1;
            leaf.insert(1);
            int x, u;
        int k = 1;
        for(int i = 1; i < n + 1; i ++) {
            cin >> x >> u;
            if(x == 1){
                depth[k + 1] =  depth[u] + 1;
                parent[k + 1][0] = u;
                if(u != 1)
                    leaf.erase(u);
                leaf.insert(k + 1);
                if(max < depth[k + 1])
                    max = depth[k + 1];
                
                precomputeSparseMatrix(k + 1, parent);
                k ++;
            } else {
                int ans = 0;
                for(unordered_set<int>::iterator itr = leaf.begin(); itr != leaf.end(); itr ++) {
                    int l = lca(u, *itr, parent, depth);
                    
                    //cout << "checking for : " << u << " " << *itr << " lca :" << l << endl;
                    int ans1 = depth[u] + depth[*itr] - 2 * depth[l];
                    if(ans < ans1)
                        ans = ans1;
                }
                
                cout << ans << endl;
            }
        }
        
    }
    
	return 0;
}
