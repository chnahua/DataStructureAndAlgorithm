package com.ahua.exam.bytedance;


/**
 * @author huajun
 * @create 2021-04-25 15:30
 */

/*
 * 已知:
 * 配置文件个数:fileNum
 * 文件依赖关系:relyArray[]
 * 更改配置文件的次数:alterFileCount
 * 更改配置文件的先后顺序
 * 修改后配置文件后，它就不依赖根节点了
 * 每修改一次输出现存全体的文件数量（节点个数）
 */

/*
 * 需要优化的地方:
 *
 * 写一个打印当前数的方法,以便直观查看
 *
 * 添加6完成后,应该直接退出,而不该再去找5和4节点处找了
    ------添加开始------
    添加节点:6
    Order-add-sonNode: 0 ↓ 1
    Order-add-sonNode: 1 ↓ 3
    Order-add-rightNode: 3 → 2
    Order-add-sonNode: 3 ↓ 5
    Order-add-rightNode: 5 → 4
    序号为6的节点添加完成
    ------添加结束------
 * 同样,删除节点也有类似问题
 */
public class ConfigurationFile {

    public static void main(String[] args) {

        // 配置文件数量（fileNum）个
        int fileNum = 7;
        // 配置文件的依赖关系,0代表根节点，数组长度为fileNum-1,
        // 数组下标0,1,2,3,4,5及其对应值
        // 表示第1,2,3,4,5,6个文件依赖的配置文件是第0,1,1,3,3,2个
        // 如:relyArray[1]表示第2个文件依赖文件1
        // int[] relyArray = new int[fileNum - 1];
        int[] relyArray = {0, 1, 1, 3, 3, 2};
        // 要更改配置文件的次数
        int alterFileCount = 6;
        // 每次更改哪个配置文件,默认不更改根文件吧？
        //int[] alterFileArray = new int[alterFileCount];
        int[] alterFileArray = {3, 4, 5, 6, 2, 1};
        // 目前(每次删除后)还有多少节点存在
        int totalNodeNum;

        FileTree fileTree = new FileTree();
        fileTree.setRoot(new TreeNode(0, 0));

        //添加节点
        for (int i = 0; i < fileNum - 1; i++) {
            fileTree.addFileTreeNode(new TreeNode(relyArray[i], i + 1));
        }

        //目前节点数目
        totalNodeNum = fileTree.treeOrder();
        System.out.println("--目前共有" + totalNodeNum + "个节点--");

        // 保存返回的要删除的那棵树/那个节点
        FileTree retTempFileTreeToDel = new FileTree();
        // 保存返回的要删除的那棵树的根节点/那个节点
        TreeNode retTempFileTreeToDelRootNode;
        // 删除的节点个数
        int delNodeNum;

        //删除节点
        for (int j = 0; j < alterFileCount; j++) {

            // alterFileArray[j] - 1 表示要修改的文件序号(value)的对应的依赖的文件的序号(fatherValue)在relyArray[]数组中的下标
            if (relyArray[alterFileArray[j] - 1] != -1) {
                // 从原树中删除这个需要删除的节点,返回删除的以这个删除节点为根节点的子树的根节点
                // (其实就是 要删除的这个节点,只是它现在指向了它的子节点,成为了一棵树)
                retTempFileTreeToDelRootNode = fileTree.delFileTreeNode(
                        new TreeNode(relyArray[alterFileArray[j] - 1], alterFileArray[j]));
                // 创建这棵要删除的树
                retTempFileTreeToDel.setRoot(retTempFileTreeToDelRootNode);
                // 调用重载方法treeOrder(relyArray),会将要删除的这颗子树上的所有节点的依赖的值在原relyArray数组中更改为-1,
                // 即表示下次再删除retTempFileTreeToDelRootNode当前节点的所有子节点时,
                // 不用再次遍历(执行delFileTreeNode方法)原树中是否有这些节点了,
                // 因为通过比较relyArray[alterFileArray[j] - 1]的值是否等于-1,就可确定要删除的节点是否已经被删除，从而避免遍历原树
                delNodeNum = retTempFileTreeToDel.treeOrder(relyArray);
                totalNodeNum -= delNodeNum;
                System.out.println("第" + (j + 1) + "次删除:" +
                        "删除了" + delNodeNum + "个节点,删除后共有" + totalNodeNum + "个节点");
/*                // 这种方式不能完全优化
                for (int k = 0; k < fileNum - 1; k++) {
                    // alterFileArray[j]:要删除的节点的序号
                    // relyArray[k]:某个节点依赖的父节点的序号
                    // 如果两者两等,则说明,某个节点依赖的父节点被删除了,此节点也从树中删除了
                    // 换句话说:删除的这个节点不会是其它任何节点的父节点或者依赖了
                    if (relyArray[k] == alterFileArray[j]) {
                        // 设为-1,当下次要删除父节点value为alterFileArray[j]的节点时,就不用再去删除
                        relyArray[k] = -1;
                    }
                }
                // 将被删除的节点的依赖节点序号设置为-1
                relyArray[alterFileArray[j] - 1] = -1;*/
            } else {
                System.out.println("第" + (j + 1) + "次删除:" + alterFileArray[j] + "号节点已不存在,无法删除,此时共有" + totalNodeNum + "个节点");
            }
        }

        //目前节点数目
        totalNodeNum = fileTree.treeOrder();
        System.out.println("--目前共有" + totalNodeNum + "个节点--");
    }
}

class FileTree {

    private TreeNode root;

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    //树添加节点,底层调用节点添加节点的方式
    public void addFileTreeNode(TreeNode node) {

        if (node == null || node.getValue() == -1) {
            return;
        }

        if (root == null) {
            //如果root为空,则直接让root指向node
            root = node;
        } else {
            System.out.println("------添加开始------");
            System.out.println("添加节点:" + node.getValue());
            boolean bool = root.addNode(node);
            if (bool) {
                System.out.println("序号为" + node.getValue() + "的节点添加成功");
            } else {
                System.out.println("序号为" + node.getValue() + "的节点添加失败");
            }
            System.out.println("------添加结束------\n");
        }
    }

    public TreeNode delFileTreeNode(TreeNode node) {

        // 要删除的节点为空 || 根节点为空 || 要删除的节点的value值为0(序号为0，即删除的是根节点,默认不删除根节点)
        // 以上三种情况,直接返回退出
        if (node == null || root == null || node.getValue() == 0) {
            return null;
        }
        System.out.println("------删除节点------");
        System.out.println("删除节点:" + node.getValue());
        return root.delNode(node);
    }

    public int treeOrder() {

        if (root == null) {
            return 0;
        }

        return root.order();
    }

    public int treeOrder(int[] arr) {

        if (root == null) {
            return 0;
        }

        return root.order(arr);
    }
}

class TreeNode {

    private final int fatherValue;//表示当前节点的父节点的序号
    private final int value;//表示第几个节点，即节点序号
    private TreeNode sonNode;//表示当前节点的子节点
    //    private TreeNode leftBroNode;//表示当前节点的左兄弟节点,这个左兄弟节点没必要啊
    private TreeNode rightBroNode;//表示当前节点的右兄弟节点

    public TreeNode(int fatherValue, int value) {
        this.fatherValue = fatherValue;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    // 添加节点
    public boolean addNode(TreeNode node) {

//        // 这里就不需要再判断了
//        if (node == null) {
//            return;
//        }

        // 当前添加的这个节点已经存在
        if (this.value == node.value) {
            return false;
        }

        // flag标记是否添加成功,若添加成功,提前结束遍历,而不用再返回时继续遍历其它节点
        boolean flag = false;

        // 找父节点
        // 此节点为node的父节点
        if (this.value == node.fatherValue) {
            // 此父节点是否有子节点
            if (this.sonNode == null) {
                // 无子节点，直接添加，令新加入的这个node节点为当前节点的子节点
                this.sonNode = node;
            } else {
                // 有子节点
//                // 令新加入的这个node节点为当前节点的子节点的左兄弟节点
//                this.sonNode.leftBroNode = node;
                // 令当前节点的子节点为新加入的这个node节点的右兄弟节点
                node.rightBroNode = this.sonNode;
                // 令新加入的这个node节点为当前节点的子节点
                this.sonNode = node;
            }
            // flag置为真,添加成功
            flag = true;
        } else {
            // 此节点不是node的父节点,未找到,继续找
            // 先找兄弟节点
            if (this.rightBroNode != null) {
                System.out.println("Order-add-rightNode: " + this.getValue() + " → " + this.rightBroNode.getValue());
                flag = this.rightBroNode.addNode(node);
            }
            // 再找儿子节点
            // 当flag为true时,表明已经添加成功,无需再继续遍历其它节点了
            if (!flag && this.sonNode != null) {
                System.out.println("Order-add-sonNode: " + this.getValue() + " ↓ " + this.sonNode.getValue());
                flag = this.sonNode.addNode(node);
            }
        }
        return flag;
    }

    // 返回以要删除的这个node节点为根节点的这棵子树
    public TreeNode delNode(TreeNode node) {

//        //删除的节点个数
//        int delNodeNum = 0;

        // 创建tempNodeToDel临时节点,最后保存为要删除的这棵树
        TreeNode tempNodeToDel = null;
        // 找到要删除的这个node节点——为当前节点的子节点或子节点的兄弟节点(如果存在的话)
        if (this.value == node.fatherValue) {

            // 如果当前节点无子节点,则不存在,则返回0,无法删除
            if (this.sonNode == null) {
                return null;
            }

            // tempNodeToDel临时节点保存this.sonNode,后续会成为要删除的节点
            // 之所以放在下面if外,是因为在下面if和else中都会用到这个临时变量,并且初值都为this.sonNode
            tempNodeToDel = this.sonNode;
            // 当前节点的子节点存在,并且正好为要删除的这个node节点,那就删除它
            if (this.sonNode.value == node.value) {
                // 注意这里如果子节点没有兄弟节点的话,this.sonNode.rightBroNode直接就是null
                this.sonNode = this.sonNode.rightBroNode;
                tempNodeToDel.rightBroNode = null;
//                // 对此要删除的节点遍历得到删除的节点数
//                delNodeNum = tempNodeToDel.order();
//                System.out.println("son:删除" + delNodeNum + "个节点");
//                return delNodeNum;
                return tempNodeToDel;
            } else {
                // 当前节点的子节点不是要删除的节点,接下来在它的兄弟节点中查找是否存在要删除的这个node节点
                // 如果未找到,一直循环查找，直到临时节点无兄弟节点,即遍历到最后一个节点仍未找到
                // 重设tempNodeToDel为this.sonNode的兄弟节点,可能无兄弟节点,就为null
                // tempNodeToDel = tempNodeToDel.rightBroNode;
                while (tempNodeToDel.rightBroNode != null) {
                    // 找到了删除的节点并删除
                    if (tempNodeToDel.rightBroNode.value == node.value) {
                        // tempNode临时保存要删掉的节点tempNodeToDel.rightBroNode(此时还包含了它可能有的兄弟节点,故需要在下一步置为null)
                        TreeNode tempNode = tempNodeToDel.rightBroNode;
                        // 当恰好tempNodeToDel.rightBroNode为最后一个兄弟节点时,此时删除的就是最后一个节点,
                        // tempNodeToDel.rightBroNode.rightBroNode指向为空,
                        // 赋值后tempNodeToDel.rightBroNode也指向为空,
                        // 即tempNodeToDel(原倒数第二个兄弟节点)成为了最后一个兄弟节点
                        tempNodeToDel.rightBroNode = tempNodeToDel.rightBroNode.rightBroNode;
                        // 将临时节点的兄弟节点置为null,此行代码位置不要变动
                        tempNode.rightBroNode = null;
                        // 此时tempNodeToDel为要删除的那个节点(只可能有子节点,它无兄弟节点)
                        tempNodeToDel = tempNode;
//                        // 对此要删除的节点遍历得到删除的节点数
//                        delNodeNum = tempNodeToDel.order();
//                        System.out.println("bro:删除" + delNodeNum + "个节点");
//                        return delNodeNum;
                        return tempNodeToDel;
                    } else {
                        // 此次未找到，则令tempNodeToDel指向下一个兄弟节点
                        // 当tempNodeToDel为倒数第二个兄弟节点,tempNodeToDel.rightBroNode为最后一个兄弟节点
                        // tempNodeToDel.rightBroNode也不是要删除的节点,说明未找到要删除的节点,
                        // 赋值后,tempNodeToDel为最后一个兄弟节点,下一次循环条件判断时就会退出循环了
                        tempNodeToDel = tempNodeToDel.rightBroNode;
                    }
                }
                // 当tempNodeToDel是最后一个兄弟节点,但是它并不是要删除的节点时,在退出上面后的while后将其置null,
                // 如果不置为null,在返回tempNodeToDel时,会把这种特殊情况给返回,造成找到并且删除的假象
                //if (tempNodeToDel.rightBroNode == null && tempNodeToDel.value != node.value) {
                if (tempNodeToDel.value != node.value) {
                    tempNodeToDel = null;
                }
                //此处返回的tempNodeToDel肯定为null
                return tempNodeToDel;
            }
        }// else {

        // 当前节点的子节点和兄弟节点（不论存在与否）都不会是要删除的这个node节点
        // 接下来遍历此节点的兄弟节点和子节点,看它们的子节点和兄弟节点中是否能找得到要删除的这个node节点

        // 此处与addNode方法中的遍历部分类似

        if (this.rightBroNode != null) {
//            System.out.println("Order-del-rightNode: " + this.getValue() + " → " + this.rightBroNode.getValue());
//            delNodeNum = this.rightBroNode.delNode(node);
            tempNodeToDel = this.rightBroNode.delNode(node);
        }

        // tempNodeToDel == null 表示未找到,未删除,而此时它的子节点还不为null,则继续查找
        // 之所以多了这个判断条件,是为了避免在上个if中如果找到并且删除那个节点后,tempNodeToDel已不为null,
        // 而当其返回到此层时,还会查找this的子节点中是否有要删除的节点,但实际上这根本没必要再继续遍历,因为根本找不到
        // 故多此判断,提前结束程序
        if (tempNodeToDel == null && this.sonNode != null) {
//            System.out.println("Order-del-sonNode: " + this.getValue() + " ↓ " + this.sonNode.getValue());
//            delNodeNum = this.sonNode.delNode(node);
//            tempNodeToDel = this.sonNode.delNode(node);
            tempNodeToDel = this.sonNode.delNode(node);
        }
        //}
        return tempNodeToDel;
    }

    //遍历得到该(子)树的节点个数
    public int order() {

        int nodeNum = 0;

        if (this.value != -1) {
            nodeNum++;
            if (this.rightBroNode != null) {
//                System.out.println("Order rightTreeNode: " + this.getValue() + " → " + this.rightBroNode.getValue());
                nodeNum += this.rightBroNode.order();
            }
            if (this.sonNode != null) {
//                System.out.println("Order sonTreeNode: " + this.getValue() + " ↓ " + this.sonNode.getValue());
                nodeNum += this.sonNode.order();
            }
        }

        return nodeNum;
    }

    // 重载:遍历要删除的这棵树/这个节点,会更改掉每个节点依赖为-1
    public int order(int[] arr) {

        int nodeNum = 0;

        if (this.value != -1) {
            nodeNum++;
            arr[this.value - 1] = -1;
            if (this.rightBroNode != null) {
                nodeNum += this.rightBroNode.order(arr);
            }
            if (this.sonNode != null) {
                nodeNum += this.sonNode.order(arr);
            }
        }

        return nodeNum;
    }
}