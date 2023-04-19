# trees-6
`BSTrees` is a library that allows you to use 3 types of binary search trees: randomized BSTree, AVLTree, RBTree.

## How to use

To build the library run
```bash
  ./gradlew build
```
 To create each of the tree views, you can use:
 ```kotlin
val rbTree = RBTree<Int, Int>() // instantiate empty red-black tree
val avlTree = AvlTree<Int, String>() // instantiate empty AVL tree
val bsTree = BSTree<String, Int>() // instantiate empty simple tree
```
The first type parameter is a comparable key. \
The second type parameter is a stored value. It can be anything

Each tree supports 3 basic operations: `insert`, `search`, `delete`.
 ```kotlin
val bsTree = BSTree<String, Int>()

bsTree.insert("bruh", 5)
bsTree.find("bruh") // 5
bsTree.delete("bruh")
```

## Storing BSTs
Currently, only storage in SQLite is supported  \
(WIP) storing in JSON, neo4j 
  

## License
Distributed under the Apache 2.0 License. See [LICENSE](https://github.com/spbu-coding-2022/trees-6/blob/main/LICENSE) for more information.
