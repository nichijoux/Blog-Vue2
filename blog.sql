/*
 Navicat Premium Data Transfer

 Source Server         : nichijoux
 Source Server Type    : MySQL
 Source Server Version : 80023 (8.0.23)
 Source Host           : localhost:3306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 80023 (8.0.23)
 File Encoding         : 65001

 Date: 22/01/2023 09:35:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` bigint NOT NULL COMMENT '文章id',
  `title` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章内容',
  `summary` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章摘要',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '封面地址',
  `is_commentable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否可评论，1为可评论，0为不可评论',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '发布状态，1为发表，0为未发表',
  `is_recommend` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否推荐，1为推荐，0为不推荐',
  `view_count` int NOT NULL DEFAULT 0 COMMENT '浏览数量',
  `create_by` bigint NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除，1删除，0未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '博客表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (1, '软件测试概述', '# 软件测试复习\n\n## Lec1——测试概述\n\n### 测试分类\n\n##### 静态分析\n\n通过扫描代码中的可疑模式来识别软件中的特定问题(例如，内存泄漏)\n\n局限性：\n\n1. 问题类型有限\n2. 误报\n\n##### 测试（最常使用）\n\n将输入输入到软件中，然后运行它，看看它的行为是否符合预期\n局限性：\n\n1. 不可能覆盖所有可能的执行\n2. 需要测试预言\n\n##### 形式化验证\n\n考虑所有可能的程序执行，并正式证明程序是正确的或错误的\n局限性：\n\n1. 很难有一个正式的规范\n\n2. 大多数真实世界的程序太昂贵而无法证明\n\n\n\n### *PIE* 模型（只关心 *Failure*，分辨 *bug*）\n\n***Software Fault***：软件中的**静态**缺陷（即缺陷）——直观理解为代码上的错误\n\n***Software Error***：错误的**内部状态**，是一些错误的表现——直观理解为运行时内部变量不正确\n\n***Software Failure***：**外部的，与需求或预期行为的其他描述相关的不正确行为**——直观理解为 ***Error***传播到软件外部，使得结果失效的行为\n\n***Bug***：对 ***Failure*** 的非正式说法\n\n###### 观察到 *Failure* 需要的三个必要条件\n\n1. **错误的代码**必须可达，必须能够被执行（***Execution/Reachability***—可达性）\n2. 在执行错误代码的时候必须触发一个**错误的中间状态**（***Infection***—感染）\n3. 错误的中间状态必须传播到最后输出，使得观测到**输出结果与预期结果不一致**（***Propagation***—传播）\n\n###### *Testing* 和 *Debugging*的区别\n\n***Testing​*** 是为了通过**执行测试用例并观察** ***Failure*** 来发现 ***bug***\n\n***Debugging*** 是为了通过**定位、理解并纠正** ***Fault*** 来修复 ***bug***\n\n### *V* 模型\n\n![VModel.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/9925013ec5934b84b0d7d56854885589.jpg)\n\n### 测试类型\n\n**单元测试**：各模块测试（测试**函数**、**类**、**组件**等）\n\n**集成测试**：测试模块之间的交互\n\n**系统测试**：由开发人员对系统进行整体测试\n\n**验收测试**：客户在没有正式测试用例的情况下，根据用户需求验证系统\n\n## Lec2——单元测试（优势，怎么进行即排测试顺序）\n\n单元测试需要测试软件的基本模块——**函数**、**类**、**组件**等\n\n典型的问题有：**局部数据结构**、**算法**、**边界条件**、**错误处理**等\n\n#### 优势\n\n1. 避免编写**模拟类**，减小工作量\n2. 当测试一个模块时，它**所依赖的模块**是**可靠**的\n3. 将系统分解为多个单元，缩小了可能出现问题的规模\n\n#### 进行单元测试的方法\n\n思想：**拓扑排序**，对所有需要进行单元测试的**模块**进行图排序，每次选择入度为0的节点进行测试\n\n#### 单元测试的框架\n\n1. ***xUnit***\n2. ***JUnit***\n\n## Lec3——白盒测试\n\n### 图（*Graph*）的形式化定义\n\n$G=(V,E)$ 为一个图，则\n\n$V$ ：**有限**、**非空**的**顶点集合**\n\n$E$ ：**顶点对**的**集合**\n\n$V_s$ ：**起始节点**的**集合** ，且 $V_s \\subset V$\n\n$V_f$ ：**终结节点**的**集合** ，且 $V_f \\subset V$\n\n### 路径（*Path*）\n\n路径（***Path***）：一组**顶点序列**如$[V_1,V_2,\\cdots,V_n]$ 且 $V_i \\in V(1\\le i \\le n)\\;and\\;(V_{i-1},V_i)\\in E$ ，即序列中相邻两个顶点间应该存在边\n\n路径的长度（***Length***）：路径的长度为**边的数量**，单节点的路径长度则为0\n\n子路径（***subpath***）：路径（***path***）中的一个连续子序列\n\n- 如存在如图所示的图 *G* ，则\n\n![PathGraph.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/6718a69a72514adf9eade37bde18036c.jpg)\n\n存在路径 $[V_1,V_2,V_4,V_6]$ ，且其长度为 3，一个子路径为 $[V_1,V_2,V_4]$\n\n### 测试路径（*Test Path*）与路径的区别\n\n测试路径（***Test Path***）：从**起始节点 **$V_s$ 开始，并以**终结节点**  $V_f$ 结束的一条**路径**（***path***）\n\n上图的测试路径（*Test path*）为\n\n- $p_1=V_1V_2V_4V_5V_7$\n- $p_2=V_1V_2V_4V_6V_7$\n- $p_3=V_1V_3V_4V_5V_7$\n- $p_4=V_1V_3V_4V_6V_7$\n\n### 图覆盖准则（*Graph Coverage Criteria*）\n\n##### 可达性\n\n- 如果图 *G* 中存在一条以 $V_i$ 为起点、$V_j$ 为终点的路径则称 $V_i$ 到 $V_j$ 可达\n\n- 如果图 $G$ 中存在一条以 $V_i$ 为起点、$V_k \\in G^{\'}$ 为终点的路径则称 $V_i$ 可达 $G^{\'}$\n\n- **语法可达（*Syntactic reach*）**：图 $G$ 中存在一条路径 $P$\n- **语义可达（*Semantic reach*）**：图 $G$ 中存在一条路径 $P$ 且该路径可以被一个测试用例完整执行\n\n##### 测试准则（*Test Criteria*）\n\n**测试要求（*Test Requirements*）**（***TR***）：描述测试路径的属性\n\n**测试标准（*Test Criterion*）**：定义测试需求的规则\n\n**满意度（*Satisfaction*）**：给定准则 ***C*** 的测试需求集合 ***TR***，对于**测试路径集** ***T*** ，当且仅当对于 ***TR*** 中的每个测试需求，**测试路径集** ***T*** 中都至少有一条测试路径满足测试需求 ***TR*** 时，称**测试路径集** ***T*** 满足测试准则 ***C***\n\n### 结构化覆盖（*Structural Coverage*）\n\n#### 节点覆盖（*Vertex Coverage*）（*VC*）\n\n- *TR* ：包含图 *G* 中**每个可达的节点**\n- 当且仅当对于图 *G* 中每个**语法可达的节点** $v\\in V$ ，都存在一条路径 *p* 包含此节点 ，最后形成 ***Path(T)*** ，则称测试用例 *T* 满足节点覆盖的要求\n\n#### 边覆盖（*Edge Coverage*）（*EC*）\n\n- *TR* ：包含图 *G* 中**每个可达的边**\n- 当且仅当对于图 *G* 中**每个语法可达的边** $e\\in E$ ，都存在一条路径 *p* 包含此边，最后形成 ***Path(T)*** ，则称测试用例 *T* 满足边覆盖的要求\n\n#### *VC* 与 *EC* 对比\n\n![VCEC.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/5e4d362b8ef640e2be61b932fca65669.jpg)\n\n#### 多边覆盖（*Covering Multiple Edges*）\n\n##### 边对覆盖（*Edge-Pair Coverage*）（*EPC*）\n\n***Edge-Pair Coverage***（***EPC***）：$TR=\\{\\mathop{\\cap}\\limits_{i=0}^{n} p_{i}\\;|\\;\\forall p_{i} \\in Path(G)\\;\\&\\&\\;length(p_{i})=2\\}$\n\n##### 完全路径覆盖（*Complete Path Coverage*）（*CPC*）\n\n***Complete Path Coverage***（***CPC***）：$TR=\\{\\mathop{\\cap}\\limits_{i=0}^{n} p_{i}\\;|\\;\\forall p_{i} \\in Path(G)\\}$\n\n##### *n* 路径覆盖（*n-Path Coverage*）（*nPC*）\n\n***n-Path Coverage***（***nPC***）：$TR=\\{\\mathop{\\cap}\\limits_{i=0}^{n} p_{i}\\;|\\;\\forall p_{i} \\in Path(G)\\;\\&\\&\\;length(p_{i})=n\\}$\n\n显然\n\n$VC(n=0),EC(n=1),EPC(n=2),nPC(n=n),CPC(n=\\infty)$\n\n##### 包含关系\n\n若存在两个测试准则 $C_1$ 、$C_2$ ，且对任意测试集 $T$ 都有满足准则 $C_1$ 后一定满足准则 $C_2$ 则我们称 $C_1$ 包含 $C_2$ ，写作 $C_1\\ge C_2$\n\n因此 $n_1\\ge n_2\\Rightarrow n_1PC\\ge n_2PC$\n\n但是 $C_1\\ge C_2$ 并不意味着满足准则 $C_1$ 的测试集 $T_1$ 都能检测出所有满足准则 $C_2$ 的测试集 $T_2$ 所能检测出的 ***Fault***\n\n#### 控制流图及其覆盖（*Control Flow Graph*）画CFG\n\n##### 代码覆盖（*Code Coverage*）\n\n- 控制流覆盖（***Control-flow Coverage***）\n- 语句覆盖（***Statement Coverage***）\n- 分支覆盖（***Branch Coverage***）\n- 路径覆盖（***Path Coverage***）\n\n有包含关系如下 ***Path Coverage*** $\\ge$ ***Branch Coverage*** $\\ge$ ***Statement Coverage*** \n\n**实践层面上我们很少大量使用*Path Coverage*** ，因为存在循环，导致路径无法完全被找出\n\n100%覆盖不一定可发现缺陷\n\n##### 控制流图（*Control Flow Graph*）（*CFG*）\n\n***CFG*** 是一种使用图形符号表示程序执行过程中所有可能经过路径的表示\n\n***CFG*** 图中\n\n- 节点 ***Vertex*** 包括：语句 ***statement***、语句块 ***block***、函数 ***function***、模块 ***module***\n\n- 边 ***Edge*** 包括：流 ***flow***、跳转 ***jump***、调用 ***call***\n\n基本块：只有一个入口点和一个出口点的语句序列（没有分支）\n\n### 主路径覆盖（*Prime Path Coverage*）重点掌握\n\n因为程序中存在循环，因此无法做到 ***CPC***（完全路径覆盖），为了解决这个问题出现了主路径覆盖\n\n#### 简单路径（*Simple Path*）\n\n**简单路径（*Simple Path*）**：路径 $P=[V_0V_1\\cdots V_i\\cdots V_n]$ 中每个节点最多出现一次，除非头尾相同\n\n![SimplePathExample.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/07ba331022db4b1082c6f51c380fe49a.jpg)\n\n#### 主路径（*Prime Path*）\n\n**主路径（*Prime Path*）**：一条简单路径（*Simple Path*）*P* ，且 *P* 不能成为其他简单路径的子路径\n\n![PrimePathExample.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/7e6c0842adcd41c085d0f172e7b15ab4.jpg)\n\n#### 往返路径（*Round Trip Path*）\n\n***Round-Trip Path***（***RTC***）：一条开始和结束节点相同的主路径（***Prime Path***）\n\n![RoundTripPathExample.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/a79ffb8bc5ac45d78a30bec887d60bdb.jpg)\n\n#### 主路径覆盖（*Prime Path Coverage*）\n\n***Prime Path Coverage***（***PPC***）: $TR=\\{\\mathop{\\cap}\\limits_{i=0}^{n} p_{i}\\;|\\;\\forall p_{i} \\in PrimePath(G)\\}$\n\n***PPC*** 包含 ***EC*** 和 ***VC*** ，但并不包含 ***EPC***（若一个节点 ***V*** 有一条指向自己的边，则 ***EPC*** 需要路径 $[V,V,V]$ ，但 $[V,V,V]$ 并非主路径）\n\n#### 往返覆盖（*Round Trip Coverage*）\n\n##### 简单往返覆盖（*Simple Round Trip Coverage*）（*SRTC*）\n\n***Simple Round Trip Coverage***（***SRTC***）：$TR=\\{\\mathop{\\cap}\\limits_{v \\in \nV} \\exists p_v|p_v \\in RoundTripPath(G)\\}$\n\n对图 *G* 中的每个节点 *v*，**至少存在一条**以 *v* 作为起始和终结点的 ***Round-Trip Path***\n\n\n\n##### 完全往返覆盖（*Complete Round Trip Coverage*）（*CRTC*）\n\n***Complete Round Trip Coverage***（***CRTC***）：$TR=\\{\\mathop{\\cap}\\limits_{v\\in V}\\forall p_v|p_v\\in RoundTripPath(G)\\}$\n\n对图 *G* 中的每个节点 *v* ，**所有**以 *v* 作为起始和终结点的 ***Round-Trip Path***\n\n#### *Simple & Prime Path Example*\n\n![SimplePrimeExample1.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/b2185c91cda54a39913effab8c202f64.jpg)\n\n![SimplePrimeExample2.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/fdb90f5f3065410892fb212a15a2ab8c.jpg)\n\n#### 练习题（形式：给代码画图，做某种路径的覆盖，写出测试路径，数据等）\n\n写出下图的**主路径覆盖**的测试路径集\n\n![PrimePathTest.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/2b60d022ae1246a8ae63bb35677dbf69.jpg)\n\n![PrimePathTestAnswer.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/3cb0c24f77e2464aa1eec3d9dbc00958.jpg)\n\n### 基本路径覆盖（*Basic Path Coverage*）\n\n#### 独立路径（*Independent Path*）\n\n**独立路径**（***Independent Path***）：对于一个路径集 ***PathSet*** ，路径 ***p*** 包含路径集中其他路径没有覆盖到的顶点或边时，它才独立于其他路径\n\n独立路径的个数即为环复杂度的个数。\n\n公式为 $IP(G)=E(边的个数)-V(顶点个数)+2$ ，该公式当且仅当图中终结点为一个时才可使用\n\n![CyclomaticComplexity.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/478a00f436d945ed8eb04c24c52c8d61.jpg)\n\n#### 基本路径覆盖的求法\n\n![BasicPathCoverageExample.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/1435542e7ce1496ba8e6358898f7e191.jpg)\n\n##### 包含关系\n\n***Complete Path Coverage*** $\\ge$ ***Basic Path Coverage*** $\\ge$ ***Branch Coverage***\n\n### 数据流覆盖（*Data Flow Coverage*）概念\n\n#### 数据流（*Data Flow*）相关定义\n\n***Definition***（***def***）：变量定义的位置\n\n***Use***：变量被访问的位置\n\n![DataFlowExample.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/bc64a5aad5904aa787b3aa154a606067.jpg)\n\n\n***defs*** **中的变量应该至少被使用一次**\n\n##### *Def* 集 和 *Use* 集\n\n- ***def(n)*** 或 ***def(e)***：由节点 ***n*** 或边 ***e*** 所定义的变量集合\n- ***use(n)*** 或 ***use(e)***：由节点 ***n*** 或边 ***e*** 所使用的变量集合\n\n##### *DU* 对\n\n$DU(l_i,l_j)$：变量于 $l_i$ 处定义，于 $l_j$ 处使用\n\n##### *Def-clear*\n\n以 $l_i$ 为起点、 $l_j$ 为终点的路径对于变量 ***v*** 是定义清楚的（***Definition clear***）当且仅当此路径上变量 ***v*** 没有被再次定义\n\n##### *Reach*\n\n变量 ***v*** 在 $l_i$ 处定义，在 $l_j$ 处使用的 ***Def-clear*** 路径对于变量 ***v*** 是可达的（***Reach***）\n\n##### *Du-path*\n\n***Du-path***：从变量 ***v*** 的 ***def*** 到变量 ***v*** 的 ***c-use*** 的一个 ***def-clear*** 的 ***simple*** 路径 或者 从变量 ***v*** 的 ***def*** 到变量 ***v*** 的 ***p-use*** 的一个 ***def-clear*** 的 ***loop-free*** 路径\n\n***p-use*** 和 ***c-use***\n\n![pcuse.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/3daff3fc063442809cf985a652da543f.jpg)\n\n***loop-free***：如果路径中的所有节点都不同，则该路径是无循环的\n\n![DuPathExample.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/aedeaa065c2e421b9db85e087fd3283c.jpg)\n\n#### 数据流覆盖准则（*Data Flow Coverage Criteria*）\n\n$Du(n_i,n_j,v)$： $n_i$ 到 $n_j$ 的 ***Du-path*** 集合，$v$ 为变量\n\n$Du(n_i,v)$ ：以 $n_i$ 为起点的 ***Du-path*** 集合，$v$ 为变量\n\n![DuPathPairSetExample.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/4e17baa605ed41d089fa49de04849568.jpg)\n\n##### *All-defs coverage*（*ADC*）\n\n对于变量 $v$ ，其 ***Du-Path*** 集合 $S=Du(n,v)$ ，则 ***TR*** 至少包含集合 ***S*** 中的一条路径\n\n![AllDefCoverage.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/0b38e111c47141bb9d0eebeca5358133.jpg)\n\n##### *All-uses coverage*（*AUC*）\n\n对于变量 $v$ ，其 ***Du-Path*** 集合 $S=du(n_i,n_j,v)$ ，则 ***TR*** 至少包含集合 ***S*** 中的一条路径\n\n![AllUseCoverage.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/3513d6f8af2546f29d5e9aa61bbbc3a3.jpg)\n\n##### *All-du-paths coverage*（*ADUPC*）\n\n对于变量 $v$ ，其 ***Du-Path*** 集合 $S=du(n_i,n_j,v)$ ，则 ***TR*** 应该包含集合 ***S*** 中的每条路径\n\n![AllDUPathCoverage.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/f316515bdbd14d87a5fcdc4e72e215a0.jpg)\n\n### 图覆盖准则包含关系\n\n![GraphCoverageCriteriaSubsumption.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/690a3b0f23c24c56a9d27eec93ceabb7.jpg)\n\n### 事件流图（*Event Flow Graph*）及覆盖准则（可能考画图）\n\n**事件流图**（***Event Flow Graph***）的形式化定义为：***EFG***是一个三元组 $M=(V,I,E)$\n\n1. $V$ 是代表**所有事件**的节点**集合**\n2. $I\\subseteq V$ 为**初始节点集合**\n3. $E\\subseteq V\\times V$ 为**节点对**集合，$(v_i,v_j)\\in E$ 当且仅当执行完 $v_i$ 后会立即执行 $v_j$\n\n##### 例子\n\n![EventFlowGraphExample1.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/22dbfb7c04734a6a8c938dd1de101ed2.jpg)\n\n![EventFlowGraphExample2.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/4353e25892144c86a4e2ef6f13e88059.jpg)\n\n![EventFlowGraphExample3.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/4f5925489f7f4df38615fde74ef4ff4d.jpg)\n\n##### 覆盖准则\n\n![EventFlowGraphCoverageCriteria.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/f05fcb902a584ff6a1bc1b957257071d.jpg)\n\n### 变异测试（*Mutation Test*）\n\n##### 概念\n\n变异测试将使用测试用例测试原始程序和突变程序，突变程序由原始程序在程序语句上进行单一语法更改（与原始程序仅有一处不同）\n\n目标是突变程序测试失败，从而证明测试用例的有效性\n\n##### 等效突变体\n\n可能有存活下来的不能被杀死的突变体，这些被称为等效突变体。尽管语法不同，但这些突变体通过测试是无法区分的。因此，它们必须“手工”检查。\n\n![EquivalentMutant.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/9e0d7d3019c64b838ca88af95c700989.jpg)\n\n##### 突变分数\n\n一组测试用例的突变得分是测试数据杀死的**非等效突变体**的百分比\n\n$Mutation\\;Score=\\frac{K}{T-E}\\times100\\%$\n\n***K*** **= 杀死突变体的数量**\n\n***T*** **= 突变体总数量**\n\n***E*** **= 等效突变体的数量**\n\n##### 两个假设（有自己的理解）\n\n- 称职的程序员假说\n  被测试的模块是由有能力的程序员或设计师编写的。因此，如果模块不正确，它与正确的模块最多相差一些小故障\n- 耦合效应\n  由于仅通过简单故障将所有模块与正确模块区分开来的测试套件非常敏感，它还可以隐式区分更复杂的故障。\n\n### 逻辑覆盖（*Logic Coverage*）必考\n\n##### 判定覆盖（*Decision Coverage*）（*DC*）\n\n对判断语句进行 ***true*** 和 ***false*** 的测试\n\n```c\nint foo(int x, int y) {\n	int z = y; \n	if ((x > 5) && (y > 0)) \n		 z = x; \n	return x * z; \n} \n```\n\n则测试用例只需 $\\{(6,1),(1,1)\\}$，因为\n\n$x=6,y=1\\Rightarrow (x>5)\\;\\&\\&\\;(y>0)=true$\n\n$x=1,y=1\\Rightarrow (x>5)\\;\\&\\&\\;(y>0)=false$\n\n\n\n##### 条件覆盖（*Condition Coverage*）（*CC*）\n\n对判断语句中每个判断条件进行 ***true*** 和 ***false*** 的测试，但并不要求做笛卡尔积\n\n```c\nint foo(int x, int y) {\n	int z = y; \n	if ((x > 5) && (y > 0)) \n		 z = x; \n	return x * z; \n} \n```\n\n则测试用例只需 $\\{(6,0),(0,1)\\}$，因为\n\n$x=6\\Rightarrow x>5=true\\;;\\;x=0\\Rightarrow x>5=false$\n\n$y=0\\Rightarrow y>0=false\\;;\\;y=1\\Rightarrow y>0=true$\n\n\n\n##### 条件判定覆盖（*Condition/Decision Coverage*）（*C/DC*）\n\n程序中每个判定条件至少有一次为 ***true***，有一次为 ***false***，使得程序中每个分支至少执行一次，且使得**各判定中的每个条件**获得各种可能的取值至少满足一次。\n\n```c\nint foo(int x, int y) {\n	int z = y; \n	if ((x > 5) && (y > 0)) \n		 z = x; \n	return x * z; \n} \n```\n\n则测试用例只需 $\\{(6,1),(0,0)\\}$ ，因为\n\n$x=6\\Rightarrow x>5=true\\;;\\;x=0\\Rightarrow x>5=false$\n\n$y=1\\Rightarrow y>0=true\\;;\\;y=0\\Rightarrow y>0=false$\n\n$x=6,y=1\\Rightarrow x>5\\;\\&\\&\\;y>0=true$\n\n$x=0,y=0\\Rightarrow x>5\\;\\&\\&\\;y>0=false$\n\n\n\n##### 条件组合覆盖（*Multiple Condition Coverage*）\n\n判定中条件的各种组合都至少被执行一次，相当于各个判定的值做笛卡尔积\n\n```c\nint foo(int x, int y) {\n	int z = y; \n	if ((x > 5) && (y > 0)) \n		 z = x; \n	return x * z; \n} \n```\n\n则测试用例需要 $\\{(6,1),(6,0),(5,1),(0,0)\\}$\n\n| $x>5$ | $y>0$  | $x>5\\;\\&\\&\\;y>0$ |\n| :---: | :--: | :-------: |\n| $true$ | $true$ | $true$ |\n| $true$ | $false$ | $false$ |\n| $false$ | $true$ | $false$ |\n| $false$ | $false$ | $false$ |\n\n\n\n##### 修正条件判定覆盖（*Modified Condition/Decision Coverage*）（*MC/DC*）\n\n***MC/DC*** 除了要满足 ***C/DC*** 的条件外，还需要满足\n\n一个判定中的每个条件都层独立地对判定结果产生影响（所谓一个条件独立地影响判定的结果，是指固定所有其他可能的条件，仅改变该条件的值就只能使判定的结果改变）\n\n```cpp\nbool function(bool A,bool B,bool c){\n    if(A && (B || C))\n        return true;\n    return false;\n}\n```\n\n为了得到 ***MC/DC*** 覆盖测试集，我们先找到 ***MCC*** 的覆盖测试集\n\n|$no$| $A$  | $B$  | $C$  | $A\\;\\&\\&\\;(B\\;||\\;C)$ |\n|:--:| :--: | :----: | :----: | :--------: |\n| 1 | 0 | 0 | 0 | 0 |\n| 2 | 0 | 0 | 1 | 0 |\n| 3 | 0 | 1 | 0 | 0 |\n| 4 | 0 | 1 | 1 | 0 |\n| 5 | 1 | 0 | 0 | 0 |\n| 6 | 1 | 0 | 1 | 1 |\n| 7 | 1 | 1 | 0 | 1 |\n| 8 | 1 | 1 | 1 | 1 |\n\n则对于条件 $A$ 来说，其独立影响结果的有 $(2,6)、(3,7)、(4,8)$ 这三对（其余条件相同、但自身不同，且导致结果不同）\n\n同理对于条件 $B$ 来说，其独立影响结果的有 $(5,7)$ ；对条件 $C$ 来说，其独立影响结果的有 $(5,6)$\n\n因此，最后我们可选取集合 $\\{3,5,6,7\\}$ 形成 ***MC/DC*** 组合\n\n##### 包含关系\n\n$DC\\ge SC\\;;\\;CC\\;not\\ge SC$\n\n$DC\\;not\\ge CC;CC\\;not\\ge DC$\n\n$C/DC\\ge CC;C/DC\\ge DC$\n\n$MC/DC\\ge C/DC$\n\n## Lec5——黑盒测试\n\n#### 随机测试（*Random Test*）\n\n##### *Random Test* 定义\n\n测试用例是完全随机生成\n\n- 输入域（***Input Domains***）必须已知\n- 在输入域内随机选取测试输入\n- 容易自动化\n\n##### 随机测试的问题\n\n- 定义输入域\n- 随机机制（生成随机数、随机算法）\n- 随机性与完整性服务\n\n##### 自适应随机测试算法（*Adaptive Random Test Algorithm*）（*ART*）\n\n随机生成一个测试用例 $t$ 并运行，然后将其加入测试用例集 $T$ 中\n\n***while***（未达到停止标准）\n**{**\n		随机生成 $k$ 个候选输入 $[c_1,c_2,\\cdots,c_k]$；\n		$for\\;\\;c_i\\in[c_1,c_2,\\cdots,c_k]$ ：\n			计算 $min_{k=1}^ndistance(c_i,t_k),t_k\\in T$\n		选择一个具有最大距离的候选 $c_i$ 并运行；\n		将 $c_i$ 加入测试用例集 $T$ 中 \n**}**\n\n###### 自适应随机测试算法的问题\n\n- 距离算法的选择问题（**欧氏距离**、**欧几里得距离**等）\n- 算法复杂度大\n- ***ART*** 算法在多维条件下的有效性普遍不理想,甚至比随机测试更差\n\n##### 容易引起算法失效的典型分布\n\n![TypicalPatternByART.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/4aad1aef1158421680b91ad1b6576fbd.jpg)\n\n#### 等价类划分（*Equivalence Partitioning*）\n\n##### 等价类划分的优点\n\n- 可应用于单元测试、集成测试、系统测试等多个测试阶段\n\n- 相对容易应用，不需要自动化\n\n- 易于调整程序以获得更多或更少的测试\n\n##### 输入域（***Input Domains***）\n\n- 程序的输入域应该包含该程序的所有可能输入\n- 即使是一个很小的程序，输入域也可能很大，甚至无限大\n- 软件测试就是从整个输入域中选择一个有限的子集\n- 输入参数（***Input parameters***）定义了输入域（***Input domains***）的范围\n	- 方法的参数（***Parameters to a method***）\n	- 从文件中读取的数据（***Data read from a file***）\n	- 全局变量（***Global variables***）\n	- 用户级的输入（***User level inputs***）\n- 应该为每个输入参数的输入域（***Input domains***）划分出不同的区域（***regions***）\n- 每个区域至少选择一个值\n\n##### 如何划分域（*Partitioning Domain*）\n\n- 定义域 ***D*** 的划分方案为 ***P*** ，且划分方案 ***P*** 定义了一组块 $[b_1,b_2,\\cdots,b_n]$\n- 则划分方案 ***P*** 必须满足两个条件\n	1. $\\forall\\;b_i,b_j\\in[b_1,b_2,\\cdots,b_n](i\\neq j)\\Rightarrow\\;b_i\\cap b_j=\\emptyset$\n	2. $\\mathop{\\cup}\\limits_{i=1}^{n}b_i=D$\n\n##### 两种划分等价类的方法（*Two Approaches*）不同之处\n\n1. 基于接口的方法（***Interface-based approach***）\n\n	- 直接从单个输入参数获取特征（***Develops characteristics directly from individual input parameters***）\n	\n	- 最简单的应用（***Simplest application***）\n	\n	- 在某些情况下可以完成部分自动化（***Can be partially automated in some situations***）\n	\n2. 基于函数的方法（***Functionality-based approach***）\n\n	- 从被测程序的行为角度获取特征（***Develops characteristics from a behavioral view of the program under test***）\n	- 更难开发——需要更多的设计工作（***Harder to develop—requires more design effort***）\n	- ***May result in better tests, or fewer tests that are as effective***\n\n##### 基于接口的方法（*Interface-based approach*）\n\n###### 使用时注意事项\n\n- 孤立地考虑每个参数，并忽略参数间的关系\n- 这是一种主要依赖于语法的简单的建模技术\n\n![InterfaceBasedApproach.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/0f2b8e7caaaa4055b823aca6b733e6ed.jpg)\n\n###### 例子\n\n假设存在一个函数 ，其声明为`TriTyp(int,int,int)` ，则 ***Interface-Based Approach*** 将考虑**各个参数的范围**，即该函数的 ***characteristic***\n\n1. 若仅考虑参数与 0 的关系（***Relation of side with zero***）\n\n![InterfaceSide0.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/b711111704b64271a005b6eea5f05866.jpg)\n\n![InterfaceSide1.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/e4b3a6fc385043d2ad793fe4d93dd33c.jpg)\n\n##### 基于函数的方法（*Functionality-based approach*）\n\n###### 使用时注意事项\n\n- 提取与预期功能相对应的特征\n- 需要测试人员更多的设计工作\n- 能够整合输入域和语义知识\n- **可以考虑参数之间的关系**\n- 建模可以基于需求，而不是实现\n- 相同的参数可能出现在多个特征中，因此很难将值转换为测试用例\n\n\n\n###### 例子\n\n假设存在一个函数 ，其声明为`TriTyp(int,int,int)` ，则 ***Functionality-Based Approach*** 将考虑**函数的作用**（三条边组合成一个三角形），即该函数的 ***characteristic*** \n\n\n\n考虑三角形的类型（***type of triangle***）\n\n![FunctionalityType0.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/9f4fc39047ed4d5088c548ced1e8b96b.jpg)\n\n即正确的根据三角形特征划分法为\n\n| *Characteristic* |       *type1*        |      *type2*       |  *type3*   |    *type4*     |\n| :--------------: | :------------------: | :----------------: | :--------: | :------------: |\n|     几何分类     | 三边均不相等的三角形 | 等腰但不等边三角形 | 等边三角形 | 无法构成三角形 |\n\n#### 边界值分析（*Boundary-value Analysis*）\n\n***bug*** 常常发生在边界值处，因此使用边界值分析法是一个好方法\n\n##### 部分常用类型的值或范围\n\n![BoundaryValue1.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/12364388d158460bb420e92215470252.jpg)\n\n![BoundaryValue2.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/bf68ddd630b848b68f783e922779e267.jpg)\n\n![BoundaryValue3.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/eb11f39cdcb0450483ea2a9b138884fd.jpg)\n\n##### 如何选取边界值\n\n###### 弱边界分析（*Weak Boundary Analysis*）\n\n对于一个常见变量$v$ ，其取值范围为$[MIN,MAX]$，则应该选取的边界值为 $\\{MIN,MIN+,Normal,MAX-,MAX\\}$\n\n![WeakBoundaryAnalysis.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/4a6291854a4448818ef0dc7a7df9cb98.jpg)\n\n###### 增强型弱边界分析（*Weak Robust-Boundary Analysis*）\n\n对于一个常见变量$v$ ，其取值范围为$[MIN,MAX]$，则应该选择的边界值为 $\\{MIN-,MIN,MIN+,Normal,MAX-,MAX,MAX+\\}$\n\n![WeakRobustBoundaryAnalysis.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/550e673095214ef0bc0ed027132db0f2.jpg)\n\n##### 强边界分析（*Strong Boundary Analysis*）\n\n强边界分析针对的是多个变量组合时的选取，即\n\n![StrongBoundaryAnalysis.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/877579b761054ebdb6e61325d00babed.jpg)\n\n![StrongBoundaryAnalysisExample.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/5aedeac6331a41aca4f3a3b2fb2171c0.jpg)\n\n##### 增强型强边界分析（*Strong Robust-Boundary Analysis*）\n\n增强型强边界分析针对的也是多个变量组合时边界值的选取问题，且在强边界分析的情况下还考虑了非法输入的问题\n\n![StrongRobustBoundaryAnalysis.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/418cca6ce74e4ff49e07248f07fd52f8.jpg)\n\n##### 边界值分析的例子\n\n![BoundaryAnalysisNextDate.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/098b530961964c2f905adbd0b60ae6a2.jpg)\n\n#### 组合测试（*Combinatiorial Test*）\n\n测试用例可以通过**等价类划分**减少测试用例数\n\n![WeakEquivalenceClassTest.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/eb44015c53124fa88680bd6b106ea147.jpg)\n\n![StrongEquivalenceClassTest.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/d24766c371cd43c9b943ecc7546b157b.jpg)\n\n但当输入纬度过多时，只使用**等价类划分**获取的测试用例仍然过多，因此提出了**组合（结队）测试**（***Pair-wise Test***）\n\n##### *Pair-wise* 算法基于的假设\n\n- 每一个维度都是正交的，即每一个维度互相都没有交集；\n- 根据数学统计分析，73%的缺陷（单因子是35%，双因子是38%）是由单因子或2个因子相互作用产生的，19%的缺陷是由3个因子相互作用产生的。因此，Pairwise基于覆盖所有2因子的交互作用产生的用例集合性价比最高而产生的。\n\n##### *Pair-wise* 算法实例\n\n若假设有输入 $InputA=\\{M,O,P\\},InputB=\\{W,L,I\\},InputC=\\{C,E\\}$ ，则不使用 ***Pair-wise*** 算法的情况下，测试用例数量为 $3\\times3\\times2=18$ 个\n\n不使用 ***Pair-wise*** 算法，则我们的测试用例应该为\n\n| *no* | *InputA* | *InputB* | *InputC* |\n| :--: | :------: | :------: | :------: |\n|  1   |    M     |    W     |    C     |\n|  2   |    M     |    W     |    E     |\n|  3   |    M     |    L     |    C     |\n|  4   |    M     |    L     |    E     |\n|  5   |    M     |    I     |    C     |\n|  6   |    M     |    I     |    E     |\n|  7   |    O     |    W     |    C     |\n|  8   |    O     |    W     |    E     |\n|  9   |    O     |    L     |    C     |\n|  10  |    O     |    L     |    E     |\n|  11  |    O     |    I     |    C     |\n|  12  |    O     |    I     |    E     |\n|  13  |    P     |    W     |    C     |\n|  14  |    P     |    W     |    E     |\n|  15  |    P     |    L     |    C     |\n|  16  |    P     |    L     |    E     |\n|  17  |    P     |    I     |    C     |\n|  18  |    P     |    I     |    E     |\n\n使用 ***Pair-wise*** 算法时，若我们以18号测试用例开始，则发现其两两组合为 ***PI***、***PE***、***IE***，而这三个组合分别在17、16、12号用例中出现过，因此可以舍去18号用例\n\n重复上述操作，最终我们得到\n| *no* | *InputA* | *InputB* | *InputC* |\n| :--: | :------: | :------: | :------: |\n|  1   |    M     |    W     |    C     |\n|  2   |    M     |    L     |    E     |\n|  3   |    M     |    I     |    E     |\n|  4   |    O     |    W     |    E     |\n|  5   |    O     |    L     |    C     |\n|  6   |    O     |    I     |    C     |\n|  7   |    P     |    W     |    E     |\n|  8   |    P     |    L     |    C     |\n|  9   |    P     |    I     |    C     |\n\n**研究证明，不同顺序执行测试用例最后剩下的测试用例数量一定相同，但可以有不同的用例组合**\n\n##### 扩展的 *t-wise/t-ways Combinatiorial Test*\n\n![TWaysCombinatiorialTest.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/30cd0342dad741149bc965ae8605d320.jpg)\n\n##### 总结\n\n![CombinatiorialTestSummary.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/7828547c8635428eb08e30a0b5818efc.jpg)\n\n#### 测试中的默认值（*Default Options in Test*）\n\n##### 单默认值\n\n![DefaultSingleOption.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/8cea169c20f245fdaa2df85b75b5aa08.jpg)\n\n#### 测试约束（*Constraint in Test*）\n\n![MergeInputVariables.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/0ae34750098e4e508555050c342580ab.jpg)\n\n![RefineInputDomain.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/0daa8026e8514c7bb17bfbf933cf04b8.jpg)\n\n![ModifyTestCase.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/b5f60e422a214f80bd67f5f180319fe0.jpg)\n\n#### 决策表（*Decision Tables*）重点掌握\n\n##### 决策表定义\n\n决策表通常由以下4部分组成：\n\n- 条件桩—列出问题的所有条件\n\n- 条件项—针对条件桩中条件列出所有可能的取值\n\n- 动作桩—列出问题规定的可能采取的操作\n\n- 动作项—指出条件项各取值情况下应采取的动作 \n\n![DecisionTable.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/475a8386220047689d5c24a5fe979869.jpg)\n\n##### 适用范围\n\n在一些数据处理问题当中，某些操作的实施依赖于多个逻辑条件的组合，即：针对不同逻辑条件的组合值，分别执行不同的操作。决策表很适合于处理这类问题。\n\n##### 决策表优点\n\n能够将复杂的问题按照各种可能的情况全部列举出来，简明并避免遗漏。因此，利用决策表能够设计出完整的测试用例集合。\n\n##### 决策表的生成\n\n1. 确定规则的个数\n	- 有 $n$ 个条件的决策表有 $2^n$ 个规则（每个条件取真、假值）\n2. 列出所有的条件桩和动作桩\n3. 填入条件项\n4. 填入动作项，得到初始决策表\n5. 简化决策表，合并相似规则\n	- 若表中有两条以上规则具有相同的动作，并且在条件项之间存在极为相似的关系，便可以合并\n\n	- 合并后的条件项用符号“-”表示，说明执行的动作与该条件的取值无关，称为无关条件\n\n例子：\n\n![DecisionTableExample.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/9b2e6e7a77d34671afc9c2849dc2b05d.jpg)\n\n##### 练习题——前一日函数\n\n- 前一日函数DPreate是NextDate的逆函数，即给定一个月份、日期、年，会返回前一天的日期。\n\n- 年的取值：$[1812年，2017年]$\n\n- 日的取值：$[1日，31日]$\n\n- 月的取值：$[1月，12月]$\n\n![DecisionTableExercise1.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/513c973085d84dc7a3469a0512f31d16.jpg)\n\n![DecisionTableExercise2.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/ff2f3c23044f4b50b526e2ffa24d57ae.jpg)\n\n![DecisionTableExercise3.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/904168586c8c4e35b603a5c007b5f73f.jpg)\n\n## Lec6——测试前沿专题\n\n### 多样性（*Diversity*）直接看*PPT*\n\n### 测试预言（*Test Oracle*）\n\n##### 内涵\n\n***Test Oracle*** 的直接含义即是**测试预期输出**，***No Test Oracle*** 就是说**预期结果不知道**\n\n##### 两个基本问题\n\n- 合适的测试准则\n- 测试预言（***Test Oracle***）\n	- 无测试预言（***No Test Oracle***）\n	- 无自动化的测试预言（***No Automated Test Oracle***）\n\n##### *No Test Oracle*\n\n- 某些类型的应用程序难以测试，因为没有“**测试预言**”\n	\n	- 比如机器学习、离散事件模拟、优化、仿真、科学计算等\n	\n- 即使没有**测试预言**，如果软件的属性被侵犯了，也有可能检测到缺陷。\n\n例子：\n\n1. $sin(x)$ 函数的测试\n\n![TestOracleExample1.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/773ead16b1244fe3a9583428d89aed37.jpg)\n\n![TestOracleExample2.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/5641922573934d2eb38ebe3343a91c28.jpg)\n\n##### *No Automated Test Oracle*\n\n- 某些类型的应用程序特别难以测试，因为没有自动化的“**测试预言**”\n	比如多媒体、人机交互等。\n\n![TestOracleExample3.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/c8c17ae423034c2b95d02f6e31352bb5.jpg)\n\n\n### 蜕变测试（*Metamorphic Testing*）重点掌握\n\n##### 概述\n\n蜕变测试是为了解决 ***Oracle*** 问题的**黑盒测试**方法，蜕变测试的理念为**利用蜕变关系使原测试用例生成新的测试用例，然后验证蜕变关系是否被保持决定测试是否通过**。\n\n**蜕变关系**（***Metamorphic Relation***）是指多次执行目标程序时，输入与输出之间期望遵循的关系\n\n![MetamorphicTest.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/ff4c656041cf462fb9b043e50da08fe3.jpg)\n\n- 新的测试用例输出 $f(t(x))$ 符合预期，函数 $f$ 不一定是正确的\n- 但是，如果 $f(x)$ 或 $f(t(x))$ 不符合预期，那么函数 $f$ 一定不正确\n\n##### 蜕变测试实例\n\n###### *No Test Oracle*\n\n1. $sin(x)$ 函数\n\n	- 测试用例 29.999^。^，则通过 $sin(x)=sin(x+2\\pi)$ ，可得到新的测试用例 29.999^。^+2$\\pi$ ，因此可以观察 $sin$(29.999^。^) 和 $sin$ (29.999^。^ $+2\\pi$) 的关系\n	\n2. 两节点间的最短路径\n\n![MetamorphicTestExample2.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/056ca6d91a204da88f1107af6392419d.jpg)\n\n##### 练习题（蜕变关系的设计）\n\n![MetamorphicTestExercise.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/8ec43638906548f3bdd0275d866eefd4.jpg)\n\n![MetamorphicTestExerciseAnswer.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/bf8682a3e5c749d18203ef11fdd633ea.jpg)\n\n###### *No Automated Test Oracle*\n\n对于此种情况，我们应该减少人工检查测试结果的成本（做不到就不做了）\n\n### 测试用例约简（*Test Suite Reduction*）约简概念，distance\n\n很多时候，我们的测试用例数量远超我们所需测试用例数，因此我们需要测试用例约简\n\n例如，存在一个程序，其总共有6条语句，语句集为 $[r_1,r_2,r_3,r_4,r_5,r_6]$ ，我们设计了 5 个测试用例 $[t_1,t_2,t_3,t_4,t_5]$ 且满足下表关系\n\n| $\\emptyset$ | $r_1$ | $r_2$ | $r_3$ | $r_4$ | $r_5$ | $r_6$ |\n| :------: | :---: | :---: | :---: | :---: | :---: | :---: |\n|  $t_1$   |   1   |   1   |   1   |   1   |   0   |   0   |\n|  $t_2$   |   0   |   0   |   1   |   1   |   1   |   0   |\n|  $t_3$   |   0   |   0   |   1   |   1   |   0   |   1   |\n|  $t_4$   |   0   |   0   |   0   |   0   |   1   |   1   |\n|  $t_5$   |   1   |   1   |   0   |   0   |   1   |   0   |\n\n原测试用例集的子集如 $\\{t_1,t_2,t_3\\},\\{t_1,t_4\\},\\{t_1,t_3,t_5\\},\\{t_2,t_4,t_5\\}$ 等均可覆盖原程序语句，而其中最小的子集为 $\\{t_1,t_4\\}$ ，这就将原测试用例约简到了最小\n\n![TestSuiteReduction.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/ee8c96656aa840fc9855788482c212a6.jpg)\n\n##### 测试用例约简的方法\n\n1. 贪心思维：每次选取未覆盖语句中最广覆盖的测试用例，直到完全覆盖原程序（大概率无法得出最简约简）\n\n2. 回溯思想：将 $n$ 个测试用例约简，即约简 $[t_1,t_2,\\cdots,t_n]$ 测试用例集的问题转换为，从 $n$ 个测试用例集中选取 $k$ 个测试用例（且这 $k$ 个测试用例能够完全覆盖源程序），而选取 $k$ 个测试用例即为排列问题\n\n##### 最小基数碰撞集\n\n***P*** 问题：能在多项式时间内解决的问题；\n\n***NP*** 问题：（***Nondeterministic Polynomial time Problem***）不能在多项式时间内解决或不确定能不能在多项式时间内解决，但能在多项式时间内验证的问题；\n\n***NPC*** 问题：（***NP Complete***）***NP*** 完全问题，所有 ***NP*** 问题在多项式时间内都能规约（***Reducibility***）到它的 ***NP*** 问题，即解决了此 ***NPC*** 问题，所有 ***NP*** 问题也都能得到解决；\n\n***NP hard*** 问题：***NP*** 难问题，所有 ***NP*** 问题在多项式时间内都能规约（***Reducibility***）到它的问题，但不一定是 ***NP*** 问题。\n\n**测试用例约简属于** ***NP hard*** **问题**\n\n![TestSuitReductionComplexity.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/7af124b1d8f048429c7cf2fd980312d2.jpg)\n\n**并非被约简了的测试用例就没用了**\n\n### 测试用例排序（*Test Case Prioritization*）\n\n##### 定义\n\n对于给定的测试用例集 $T_{init}$ ，其全排列为 $PT$ ，测试用例排序就是为了找到 $T\\in PT:T\\neq(\\forall\\;T^{\'}\\in PT)\\;\\&\\&\\;f(T)>f(T^{\'})$ ，$f$ 为目标函数（**排列得分函数**）\n\n**测试用例排序**是为了利用测试用例集**快速找到**错误，便于交给相关人员修复\n\n##### *APFD* 算法\n\n**算法核心**：计算检测到故障的平均百分比\n\n$APFD(T)=1-\\frac{tf_1+tf_2+\\cdots+tf_k}{k\\times m}+\\frac{1}{2m}$\n\n- $k$：测试集在测试程序 $P$ 得出的故障数\n\n- $m$：测试用例总数\n- $tf_i$：最早发现缺陷 $i$ 的用例序号即为 $tf_i$\n\n***APFD*** 无法达到 1\n\n##### 例子\n\n![TestCasePrioritizationExample.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/1976c0f131e149fc82fe77176a064291.jpg)\n\n$APFD(T)=1-\\frac{1+3+3+3+1+2+2+5+5+5}{10\\times 5}+\\frac{1}{2\\times 5}=1-\\frac{30}{50}+\\frac{1}{10}=50\\%$\n\n##### 复杂度\n\n![TestCasePrioritizationComplexity.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/9b2c7b62f73442b4ac0d82e2ffaff32a.jpg)\n\n### 缺陷定位（*Fault Localization*）\n\n##### 基于频率的缺陷定位（*Spectrum-based Fault Localization*）\n\n- 在**未通过**的测试用例中经常出现的程序语句很可能**包括**错误\n- 在**通过**的测试用例中经常出现的程序语句很可能**不包括**错误\n- 风险计算公式：***Tarantula*** 等\n\n$$suspiciousness(s)=\\frac{\\frac{a_{ef}}{a_{ef}+a_{nf}}}{\\frac{a_{ep}}{a_{ep}+a_{np}}+\\frac{a_{ef}}{a_{ef}+a_{nf}}}$$\n\n$a_{ef}$：失败用例中执行了语句 $s$ 的数量\n\n$a_{nf}$：失败用例中没有执行语句 $s$ 的数量\n\n$a_{ep}$：通过用例中执行了语句 $s$ 的数量\n\n$a_{np}$：通过用例中没有执行语句 $s$ 的数量\n\n##### 实例\n\n![FaultLocalizationExample.jpg](https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/41f15932c65a412890042f7fcf703eac.jpg)\n\n##### 缺陷定位中存在的问题（掌握偶然正确性，未给出语句场景概念）\n\n- 偶然正确性（***Coincidental Correctness***）（**重点，考的可能性大**）\n\n	缺陷 $fault$ 被执行到了，但可能不会发生 $Error$，或者出现了 $Error$ 但没有出现 $failure$\n\n- 多缺陷问题（***Multiple Faults***）\n\n- 未给出语句场景（***Perfect Debugger***）代码到手时，就能马上定位\n\n## Lec7——功能、性能、移动应用测试（概念）\n\n#### 功能测试\n\n- 根据产品特性和设计需求，验证一个产品的特性和行为是否满足设计需求。\n- 正确性、可靠性、易用性。\n\n#### 性能测试\n\n- 验证产品的性能在特定负载和环境条件下使用是否满足性能指标。\n- 度量方法：响应时间、并发用户数、吞吐量、性能计数器、负载测试、压力测试\n\n#### 移动应用测试\n\n- 手机系统、型号\n- 传感器与屏幕(尺寸？)碎片化\n\n#### 软件缺陷的描述\n\n- 软件缺陷的生命周期\n- 严重性和优先级\n- 缺陷的其它属性\n- 完整的缺陷信息\n- 缺陷描述的基本要求\n- 缺陷报告的示例\n\n#### 软件缺陷的生命周期\n\n- 一个软件缺陷被发现、报告到 这个缺陷被修复、验证直至最后关闭的完整过程。\n\n- 基本的缺陷生命周期\n\n	- 发现-打开:测试人员找到软件缺陷并将软件缺陷􏰁交给开发人员。\n	- 打开-修复:开发人员再现、修复缺陷，然后提交给测试人员去验证。\n	- 修复-关闭:测试人员验证修复过的软件，关闭已不存在的缺陷。\n\n- 严重性(severity)：衡量缺陷对客户满意度的影响程度\n\n	- 致命的(fatal)、严重的(critical)、一般的(major)、微小的(minor)\n\n- 优先级(Priority)：指缺陷被修复的紧急程度。\n\n	#### 缺陷描述的基本要求\n\n	- 单一准确\n	- 可以再现\n	- 完整统一\n	- 短小简练\n	- 特定条件\n	- 补充完善\n	- 不做评价', '软件测试的重点知识', 'https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/30/276637cc1e6c48e19722750a38a3f37e.jpg', 1, 1, 1, 19, 0, '2022-11-30 22:39:20', -1, '2023-01-21 10:58:55', 0);
INSERT INTO `article` VALUES (12425, 'Mrs.', 'The repository database can be an existing MySQL, MariaDB, PostgreSQL, SQL Server, or Amazon RDS instance. Secure SHell (SSH) is a program to log in into another computer over a network,              ', 'nMLuc2fd9U', 'https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/29/dd20eabbff7643389dc73e9698305bf8.png', 1, 1, 0, 622, 454, '2019-10-02 14:29:12', -1, '2023-01-21 10:58:55', 0);
INSERT INTO `article` VALUES (1599058401550196738, 'Java Sevlet', '# Servlet', 'Java Sevlet是JavaWeb中的一个重要技术', 'https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/12/03/27b7f698c0504587a6d1d1a1aeb9c685.png', 1, 1, 0, 3, 0, '2022-12-03 23:10:07', -1, '2023-01-21 10:58:55', 0);

-- ----------------------------
-- Table structure for article_tag
-- ----------------------------
DROP TABLE IF EXISTS `article_tag`;
CREATE TABLE `article_tag`  (
  `id` bigint NOT NULL COMMENT '主键',
  `article_id` bigint NOT NULL COMMENT '博客id',
  `tag_id` bigint NOT NULL COMMENT '标签id',
  `create_by` bigint NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `article_tag_id_idx`(`article_id` ASC, `tag_id` ASC) USING BTREE COMMENT '文章、标签id索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '博客、标签中间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_tag
-- ----------------------------
INSERT INTO `article_tag` VALUES (2, 5, 28, 403, '2011-05-30 06:44:44', 885, '2010-08-06 19:15:30');
INSERT INTO `article_tag` VALUES (3, 18, 14, 860, '2002-12-02 08:30:18', 621, '2019-09-12 03:02:50');
INSERT INTO `article_tag` VALUES (5, 17, 7, 82, '2014-07-08 19:01:41', 341, '2017-10-14 16:51:36');
INSERT INTO `article_tag` VALUES (6, 17, 17, 650, '2016-10-07 19:26:57', 527, '2005-05-14 07:20:18');
INSERT INTO `article_tag` VALUES (10, 6, 1, 888, '2011-09-22 06:15:04', 331, '2007-09-28 22:26:09');
INSERT INTO `article_tag` VALUES (11, 12, 3, 870, '2018-02-08 04:39:13', 841, '2007-08-11 18:33:51');
INSERT INTO `article_tag` VALUES (13, 8, 25, 479, '2018-03-02 04:50:54', 811, '2022-07-02 09:28:18');
INSERT INTO `article_tag` VALUES (19, 9, 25, 871, '2022-04-15 19:42:22', 647, '2021-06-27 14:27:43');
INSERT INTO `article_tag` VALUES (20, 5, 11, 631, '2021-09-18 21:54:42', 682, '2002-04-07 00:15:03');
INSERT INTO `article_tag` VALUES (22, 4, 17, 272, '2015-10-21 14:15:02', 331, '2008-07-13 13:13:10');
INSERT INTO `article_tag` VALUES (24, 12, 28, 938, '2012-07-06 17:42:21', 486, '2003-05-30 13:57:57');
INSERT INTO `article_tag` VALUES (26, 9, 5, 594, '2009-03-26 03:16:37', 322, '2022-07-12 17:31:33');
INSERT INTO `article_tag` VALUES (28, 19, 14, 278, '2001-08-03 23:28:17', 21, '2012-01-18 16:22:06');
INSERT INTO `article_tag` VALUES (29, 2, 3, 227, '2011-12-15 15:41:43', 383, '2001-10-04 05:29:39');
INSERT INTO `article_tag` VALUES (30, 13, 3, 26, '2008-12-13 19:24:18', 390, '2010-11-06 22:27:30');
INSERT INTO `article_tag` VALUES (31, 10, 23, 441, '2015-04-18 14:11:09', 188, '2001-07-17 13:59:21');
INSERT INTO `article_tag` VALUES (32, 6, 11, 252, '2012-11-20 04:40:47', 658, '2019-05-04 23:59:32');
INSERT INTO `article_tag` VALUES (34, 18, 10, 641, '2001-08-28 13:16:17', 495, '2000-02-29 06:27:48');
INSERT INTO `article_tag` VALUES (35, 2, 17, 78, '2014-09-04 10:25:58', 265, '2004-04-29 17:02:43');
INSERT INTO `article_tag` VALUES (37, 4, 23, 814, '2002-12-31 03:36:22', 82, '2016-12-11 15:53:48');
INSERT INTO `article_tag` VALUES (39, 2, 10, 845, '2006-10-19 06:08:28', 296, '2003-11-27 21:43:10');
INSERT INTO `article_tag` VALUES (40, 9, 11, 773, '2009-01-20 23:54:26', 403, '2009-11-26 17:46:01');
INSERT INTO `article_tag` VALUES (41, 11, 3, 132, '2018-03-14 19:31:23', 816, '2007-01-19 01:12:47');
INSERT INTO `article_tag` VALUES (42, 19, 11, 992, '2013-03-12 17:15:31', 889, '2011-08-09 14:21:34');
INSERT INTO `article_tag` VALUES (45, 19, 27, 247, '2015-11-13 01:11:27', 569, '2007-07-02 14:10:25');
INSERT INTO `article_tag` VALUES (46, 10, 27, 570, '2014-06-06 15:28:03', 250, '2014-02-14 09:21:22');
INSERT INTO `article_tag` VALUES (47, 4, 13, 28, '2021-09-29 20:35:52', 769, '2006-04-11 11:07:37');
INSERT INTO `article_tag` VALUES (49, 19, 24, 719, '2001-03-16 00:36:11', 807, '2012-04-23 18:13:34');
INSERT INTO `article_tag` VALUES (52, 18, 3, 372, '2022-06-11 14:27:15', 782, '2021-02-13 06:04:42');
INSERT INTO `article_tag` VALUES (53, 11, 7, 802, '2021-07-15 08:36:08', 627, '2018-06-23 19:49:46');
INSERT INTO `article_tag` VALUES (54, 17, 30, 480, '2017-01-28 01:48:28', 855, '2005-06-13 20:51:38');
INSERT INTO `article_tag` VALUES (55, 14, 27, 433, '2012-11-14 15:31:18', 703, '2007-06-01 20:53:18');
INSERT INTO `article_tag` VALUES (56, 7, 20, 52, '2014-08-11 22:53:35', 416, '2013-09-30 15:46:51');
INSERT INTO `article_tag` VALUES (58, 2, 21, 369, '2010-11-25 14:54:30', 253, '2020-08-24 08:35:14');
INSERT INTO `article_tag` VALUES (59, 4, 6, 369, '2002-09-29 06:13:25', 472, '2017-10-24 19:20:26');
INSERT INTO `article_tag` VALUES (60, 17, 28, 715, '2007-03-28 06:41:48', 113, '2003-10-04 08:19:57');
INSERT INTO `article_tag` VALUES (1597271871739420673, 1, 1591823685721903105, 0, '2022-11-29 00:51:05', 0, '2022-11-29 00:51:05');
INSERT INTO `article_tag` VALUES (1597972958100406273, 1597963490981658626, 1597962703857598466, 0, '2022-11-30 23:16:57', 0, '2022-11-30 23:16:57');
INSERT INTO `article_tag` VALUES (1599058902794690561, 1599058401550196738, 1597962703857598466, 0, '2022-12-03 23:12:06', 0, '2022-12-03 23:12:06');
INSERT INTO `article_tag` VALUES (1599058902794690562, 1599058401550196738, 1599056801603575809, 0, '2022-12-03 23:12:06', 0, '2022-12-03 23:12:06');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint NOT NULL COMMENT '评论id',
  `root_id` bigint NOT NULL DEFAULT -1 COMMENT '根评论id，为-1则说明自身为根评论',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  `article_id` bigint NOT NULL COMMENT '所属博客id',
  `to_comment_user_id` bigint NOT NULL COMMENT '所回复的评论的userId',
  `to_comment_id` bigint NOT NULL DEFAULT -1 COMMENT '回复目标评论id，为-1则说明自身为根评论',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '评论状态，0为正常，1为被举报',
  `create_by` bigint NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除，1删除，0未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, -1, 'MmM2WaWXqD', 9, -1, -1, 0, 0, '2000-07-30 00:56:15', 322, '2015-05-10 22:19:39', 0);
INSERT INTO `comment` VALUES (2, -1, 'dv95jujuG1', 11, -1, -1, 0, 0, '2019-04-16 19:37:41', 619, '2002-01-01 10:28:04', 0);
INSERT INTO `comment` VALUES (3, -1, 'yfsXfnrgIu', 5, -1, -1, 0, 0, '2020-11-08 14:36:26', 310, '2009-03-19 22:49:56', 0);
INSERT INTO `comment` VALUES (4, -1, 'SfYTQPUDa8', 5, -1, -1, 0, 0, '2010-07-09 10:41:38', 304, '2020-04-23 13:15:18', 0);
INSERT INTO `comment` VALUES (5, -1, 'H82QLDnYlB', 19, -1, -1, 0, 0, '2013-09-02 16:48:08', 573, '2002-01-02 14:03:38', 0);
INSERT INTO `comment` VALUES (6, -1, 'wrevXBdX9D', 5, -1, -1, 0, 0, '2002-03-15 09:15:38', 357, '2022-03-24 22:16:34', 0);
INSERT INTO `comment` VALUES (7, -1, 'CRbiFwcqbn', 13, -1, -1, 0, 0, '2014-06-10 19:49:10', 981, '2008-11-06 10:57:47', 0);
INSERT INTO `comment` VALUES (8, -1, '5B8QhvybWF', 7, -1, -1, 0, 0, '2013-09-14 12:33:05', 582, '2021-01-28 14:33:28', 0);
INSERT INTO `comment` VALUES (9, -1, 'dZjoqFPCJQ', 7, -1, -1, 0, 0, '2020-08-01 00:15:53', 49, '2007-07-31 11:32:42', 0);
INSERT INTO `comment` VALUES (10, -1, 'bdkADUFnmB', 7, -1, -1, 0, 0, '2011-01-03 20:19:05', 395, '2000-05-21 21:13:02', 0);
INSERT INTO `comment` VALUES (11, -1, 'sEJHJurTXk', 7, -1, -1, 0, 0, '2016-02-26 01:01:05', 912, '2007-01-04 17:05:40', 0);
INSERT INTO `comment` VALUES (12, -1, 'IcqBbBu5ZJ', 12, -1, -1, 0, 0, '2019-12-26 11:09:41', 395, '2022-05-02 17:20:28', 0);
INSERT INTO `comment` VALUES (13, -1, 'RD8no4WRDA', 1, -1, -1, 0, 0, '2000-06-21 04:39:25', 781, '2022-10-14 13:44:51', 0);
INSERT INTO `comment` VALUES (14, -1, '1Mh5krIMXF', 7, -1, -1, 0, 0, '2010-02-24 15:48:26', 749, '2020-03-14 06:46:39', 0);
INSERT INTO `comment` VALUES (15, -1, 'DKh6lcAliv', 7, -1, -1, 0, 0, '2017-07-21 22:52:47', 249, '2002-07-23 18:56:42', 0);
INSERT INTO `comment` VALUES (16, -1, 'vXmj2O2utl', 17, -1, -1, 0, 0, '2004-02-09 13:41:54', 249, '2020-11-24 05:00:38', 0);
INSERT INTO `comment` VALUES (17, -1, 'J6RH1dZZVw', 17, -1, -1, 0, 0, '2021-03-09 22:27:38', 570, '2020-11-10 21:38:11', 0);
INSERT INTO `comment` VALUES (18, -1, 'RviEBi3yAO', 8, -1, -1, 0, 0, '2021-01-22 13:06:00', 834, '2005-04-26 13:29:49', 0);
INSERT INTO `comment` VALUES (19, -1, 'Tq1KaDVke3', 18, -1, -1, 0, 0, '2000-04-19 04:40:22', 768, '2021-08-26 01:45:28', 0);
INSERT INTO `comment` VALUES (20, -1, 'dFw0Gg7Kdi', 4, -1, -1, 0, 0, '2018-04-20 21:10:48', 413, '2005-02-07 10:31:23', 0);
INSERT INTO `comment` VALUES (21, 7, 'MmM2WaWXqD', 9, 48, 8, 0, 0, '2000-07-30 00:56:15', 322, '2015-05-10 22:19:39', 0);
INSERT INTO `comment` VALUES (22, 17, 'dv95jujuG1', 11, 805, 6, 0, 0, '2019-04-16 19:37:41', 619, '2002-01-01 10:28:04', 0);
INSERT INTO `comment` VALUES (23, 14, 'yfsXfnrgIu', 5, 779, 6, 0, 0, '2020-11-08 14:36:26', 310, '2009-03-19 22:49:56', 0);
INSERT INTO `comment` VALUES (24, 3, 'SfYTQPUDa8', 5, 665, 17, 0, 0, '2010-07-09 10:41:38', 304, '2020-04-23 13:15:18', 0);
INSERT INTO `comment` VALUES (25, 17, 'H82QLDnYlB', 19, 852, 8, 0, 0, '2013-09-02 16:48:08', 573, '2002-01-02 14:03:38', 0);
INSERT INTO `comment` VALUES (26, 9, 'wrevXBdX9D', 5, 745, 10, 0, 0, '2002-03-15 09:15:38', 357, '2022-03-24 22:16:34', 0);
INSERT INTO `comment` VALUES (27, 18, 'CRbiFwcqbn', 13, 688, 2, 0, 0, '2014-06-10 19:49:10', 981, '2008-11-06 10:57:47', 0);
INSERT INTO `comment` VALUES (28, 8, '5B8QhvybWF', 18, 332, 17, 0, 0, '2013-09-14 12:33:05', 582, '2021-01-28 14:33:28', 0);
INSERT INTO `comment` VALUES (29, 11, 'dZjoqFPCJQ', 16, 678, 3, 0, 0, '2020-08-01 00:15:53', 49, '2007-07-31 11:32:42', 0);
INSERT INTO `comment` VALUES (30, 7, 'bdkADUFnmB', 15, 871, 15, 0, 0, '2011-01-03 20:19:05', 395, '2000-05-21 21:13:02', 0);
INSERT INTO `comment` VALUES (31, 2, 'sEJHJurTXk', 18, 36, 17, 0, 0, '2016-02-26 01:01:05', 912, '2007-01-04 17:05:40', 0);
INSERT INTO `comment` VALUES (32, 4, 'IcqBbBu5ZJ', 12, 212, 17, 0, 0, '2019-12-26 11:09:41', 395, '2022-05-02 17:20:28', 0);
INSERT INTO `comment` VALUES (33, 1, 'RD8no4WRDA', 1, 249, 7, 0, 0, '2000-06-21 04:39:25', 781, '2022-10-14 13:44:51', 0);
INSERT INTO `comment` VALUES (34, 2, '1Mh5krIMXF', 7, 580, 3, 0, 0, '2010-02-24 15:48:26', 749, '2020-03-14 06:46:39', 0);
INSERT INTO `comment` VALUES (35, 15, 'DKh6lcAliv', 7, 914, 6, 0, 0, '2017-07-21 22:52:47', 249, '2002-07-23 18:56:42', 0);
INSERT INTO `comment` VALUES (36, 2, 'vXmj2O2utl', 17, 563, 10, 0, 0, '2004-02-09 13:41:54', 249, '2020-11-24 05:00:38', 0);
INSERT INTO `comment` VALUES (37, 17, 'J6RH1dZZVw', 17, 204, 6, 0, 0, '2021-03-09 22:27:38', 570, '2020-11-10 21:38:11', 0);
INSERT INTO `comment` VALUES (38, 9, 'RviEBi3yAO', 8, 850, 7, 0, 0, '2021-01-22 13:06:00', 834, '2005-04-26 13:29:49', 0);
INSERT INTO `comment` VALUES (39, 5, 'Tq1KaDVke3', 18, 246, 11, 0, 0, '2000-04-19 04:40:22', 768, '2021-08-26 01:45:28', 0);
INSERT INTO `comment` VALUES (40, 7, 'dFw0Gg7Kdi', 4, 741, 9, 0, 0, '2018-04-20 21:10:48', 413, '2005-02-07 10:31:23', 0);
INSERT INTO `comment` VALUES (41, 4, 'hEwb7D7oDq', 20, 545, 1, 0, 0, '2022-06-09 13:26:08', 436, '2007-08-19 13:14:36', 0);
INSERT INTO `comment` VALUES (42, 12, 'zC51g5cXV4', 14, 944, 18, 0, 0, '2019-04-22 18:14:45', 472, '2006-07-11 12:10:27', 0);
INSERT INTO `comment` VALUES (43, 10, 'Dt5T0WpaXa', 15, 828, 17, 0, 0, '2002-02-17 10:06:17', 793, '2022-04-18 16:50:00', 0);
INSERT INTO `comment` VALUES (44, 4, 'opinEdHcqq', 18, 595, 7, 0, 0, '2011-12-08 01:34:47', 684, '2009-10-24 03:43:54', 0);
INSERT INTO `comment` VALUES (45, 2, 'epgRzSbtlW', 20, 55, 8, 0, 0, '2008-01-30 21:20:13', 218, '2019-11-30 14:04:22', 0);
INSERT INTO `comment` VALUES (46, 13, 'QKZGgkVlqc', 20, 774, 17, 0, 0, '2001-07-10 06:59:26', 696, '2003-11-16 01:06:04', 0);
INSERT INTO `comment` VALUES (47, 9, 'pQbIXclrWD', 2, 965, 6, 0, 0, '2015-08-15 08:47:25', 86, '2019-03-02 19:27:00', 0);
INSERT INTO `comment` VALUES (48, 11, 'ca7WoFbTkO', 7, 318, 15, 0, 0, '2007-10-12 21:07:19', 946, '2020-11-25 09:39:58', 0);
INSERT INTO `comment` VALUES (49, 15, 'qgyWw2l8cq', 4, 412, 16, 0, 0, '2014-07-30 09:22:28', 982, '2000-05-26 02:20:56', 0);
INSERT INTO `comment` VALUES (50, 11, 'rAEDotaLjN', 2, 291, 9, 0, 0, '2017-10-03 09:45:06', 613, '2004-05-20 13:52:52', 0);
INSERT INTO `comment` VALUES (51, 2, 'jrmlHU2K4H', 14, 386, 17, 0, 0, '2006-02-23 05:27:21', 578, '2001-03-07 07:10:05', 0);
INSERT INTO `comment` VALUES (52, 14, '87RrjAN207', 13, 393, 11, 0, 0, '2003-02-14 22:48:00', 889, '2019-02-08 11:38:36', 0);
INSERT INTO `comment` VALUES (53, 8, 'EyiXRCozDC', 8, 791, 6, 0, 0, '2002-06-28 05:04:02', 300, '2004-11-22 03:37:15', 0);
INSERT INTO `comment` VALUES (54, 13, 'yKgLCSw0rt', 4, 414, 2, 0, 0, '2006-12-28 14:10:55', 206, '2007-06-26 18:38:45', 0);
INSERT INTO `comment` VALUES (55, 19, 'GCKbl2EZe5', 4, 183, 6, 0, 0, '2014-10-30 07:09:37', 6, '2018-09-19 21:06:51', 0);
INSERT INTO `comment` VALUES (56, 8, 'eOirsfXNxR', 9, 489, 1, 0, 0, '2018-12-31 01:09:36', 902, '2011-12-26 07:08:52', 0);
INSERT INTO `comment` VALUES (57, 16, 'mGvDVHmbkD', 7, 952, 8, 0, 0, '2003-03-13 08:00:03', 516, '2020-06-28 08:37:55', 0);
INSERT INTO `comment` VALUES (58, 11, 'wODwDIzlll', 5, 879, 8, 0, 0, '2014-04-11 15:52:29', 698, '2014-05-01 12:10:38', 0);
INSERT INTO `comment` VALUES (59, 13, 'hay5TJteQT', 3, 209, 12, 0, 0, '2021-02-04 22:04:31', 46, '2003-04-25 20:27:10', 0);
INSERT INTO `comment` VALUES (60, 9, 'urDm7EIugZ', 11, 616, 4, 0, 0, '2017-02-08 20:36:03', 830, '2013-03-10 17:28:36', 0);
INSERT INTO `comment` VALUES (61, 4, 'zuvVsGmzoV', 17, 243, 14, 0, 0, '2009-12-08 03:40:50', 719, '2010-07-23 02:48:22', 0);
INSERT INTO `comment` VALUES (62, 3, 'trTpunvlkU', 13, 664, 1, 0, 0, '2011-11-16 00:22:33', 159, '2022-05-21 17:46:52', 0);
INSERT INTO `comment` VALUES (63, 15, 'orNKSMHhux', 7, 321, 20, 0, 0, '2016-09-26 08:32:56', 223, '2019-10-28 13:45:00', 0);
INSERT INTO `comment` VALUES (64, 5, '9sCtGqV9xB', 17, 606, 14, 0, 0, '2001-02-03 21:07:25', 801, '2008-08-31 00:48:49', 0);
INSERT INTO `comment` VALUES (65, 20, 'mgoe6RG0HP', 14, 258, 20, 0, 0, '2012-04-23 07:01:29', 74, '2008-06-23 12:02:36', 0);
INSERT INTO `comment` VALUES (66, 7, '6fDRZ8hVxo', 5, 763, 8, 0, 0, '2017-02-22 06:15:16', 197, '2004-08-13 16:34:45', 0);
INSERT INTO `comment` VALUES (67, 6, 'VbBBILjHPS', 5, 320, 10, 0, 0, '2020-06-01 19:48:41', 268, '2021-09-17 22:02:56', 0);
INSERT INTO `comment` VALUES (68, 19, 'Ruz38Y2pXz', 13, 595, 14, 0, 0, '2014-07-05 14:34:34', 752, '2015-05-09 09:28:24', 0);
INSERT INTO `comment` VALUES (69, 3, 'VNptmc5L9g', 10, 223, 8, 0, 0, '2008-11-01 13:35:24', 72, '2021-06-26 03:34:52', 0);
INSERT INTO `comment` VALUES (70, 18, 'jvw826xKRF', 18, 645, 14, 0, 0, '2008-08-04 00:55:32', 436, '2016-03-02 20:44:31', 0);
INSERT INTO `comment` VALUES (71, 12, 'YL4UhQEAUD', 17, 52, 5, 0, 0, '2008-02-04 01:58:09', 383, '2022-06-03 10:11:08', 0);
INSERT INTO `comment` VALUES (72, 4, '0K64V9mCzV', 1, 510, 2, 0, 0, '2009-07-03 09:40:00', 206, '2003-01-17 11:03:14', 0);
INSERT INTO `comment` VALUES (73, 10, '4uQ3jNrqVd', 7, 943, 2, 0, 0, '2009-01-27 14:19:04', 722, '2007-07-20 10:44:49', 0);
INSERT INTO `comment` VALUES (74, 15, 'nhDUHYOcFu', 15, 247, 19, 0, 0, '2002-09-16 06:19:09', 408, '2001-11-07 06:06:43', 0);
INSERT INTO `comment` VALUES (75, 4, 'LyOINDCugR', 10, 58, 8, 0, 0, '2016-07-30 07:03:58', 349, '2015-03-24 21:26:57', 0);
INSERT INTO `comment` VALUES (76, 4, 'r1dZ0MY7Xa', 11, 287, 17, 0, 0, '2004-03-16 17:37:34', 639, '2015-10-13 08:36:50', 0);
INSERT INTO `comment` VALUES (77, 18, 'dlGzUlL2zf', 10, 438, 6, 0, 0, '2018-07-26 14:56:38', 897, '2015-04-23 09:53:52', 0);
INSERT INTO `comment` VALUES (78, 7, 'anLdMMSnWz', 14, 287, 1, 0, 0, '2005-11-09 03:26:04', 942, '2005-02-27 07:16:11', 0);
INSERT INTO `comment` VALUES (79, 17, '7hC3H7XXNB', 3, 900, 15, 0, 0, '2012-01-30 02:01:14', 406, '2021-03-11 08:44:02', 0);
INSERT INTO `comment` VALUES (80, 12, 'pgEPUFbXdV', 4, 666, 4, 0, 0, '2014-07-20 13:36:54', 403, '2003-04-08 19:38:00', 0);
INSERT INTO `comment` VALUES (81, 4, 'l9rF1viLFH', 9, 850, 13, 0, 0, '2003-09-23 00:40:11', 744, '2004-07-09 00:14:08', 0);
INSERT INTO `comment` VALUES (82, 9, 'xh9czjRiv8', 1, 937, 3, 0, 0, '2014-06-24 03:15:24', 50, '2008-10-08 18:39:17', 0);
INSERT INTO `comment` VALUES (83, 17, 'WKYBdkznNd', 16, 357, 20, 0, 0, '2008-01-10 13:43:28', 594, '2001-03-04 23:52:03', 0);
INSERT INTO `comment` VALUES (84, 14, 'gu5TbTqsEe', 19, 189, 2, 0, 0, '2018-12-16 16:41:43', 576, '2021-01-23 19:10:29', 0);
INSERT INTO `comment` VALUES (85, 19, 'hXMxBbk4Le', 2, 237, 4, 0, 0, '2008-11-10 17:03:52', 251, '2004-06-29 17:07:55', 0);
INSERT INTO `comment` VALUES (86, 8, 'ioecx8TdJM', 19, 265, 2, 0, 0, '2014-01-26 06:19:06', 346, '2001-07-03 15:28:37', 0);
INSERT INTO `comment` VALUES (87, 9, 'gKJVuHOcvJ', 19, 197, 18, 0, 0, '2022-03-04 07:37:05', 827, '2007-01-27 13:13:20', 0);
INSERT INTO `comment` VALUES (88, 7, '7Ivyu7m1nX', 19, 409, 12, 0, 0, '2002-03-01 00:21:27', 12, '2008-08-13 02:42:16', 0);
INSERT INTO `comment` VALUES (89, 7, 'G8KGJOKHEq', 16, 952, 8, 0, 0, '2009-12-09 23:37:07', 564, '2014-10-08 20:12:50', 0);
INSERT INTO `comment` VALUES (90, 7, '10rfBgFzTb', 10, 332, 4, 0, 0, '2009-09-09 14:52:18', 762, '2010-03-29 16:49:09', 0);
INSERT INTO `comment` VALUES (91, 4, 'R3a4Buj2fm', 2, 758, 17, 0, 0, '2003-01-21 11:19:44', 973, '2003-10-06 01:49:00', 0);
INSERT INTO `comment` VALUES (92, 12, 'LL514uHDZi', 10, 708, 10, 0, 0, '2005-02-21 07:37:33', 618, '2005-02-16 01:58:46', 0);
INSERT INTO `comment` VALUES (93, 20, '5U60DTvxIn', 19, 856, 15, 0, 0, '2018-01-02 16:12:51', 74, '2012-11-09 05:24:09', 0);
INSERT INTO `comment` VALUES (94, 7, 'waRBOhMygH', 14, 809, 16, 0, 0, '2020-07-28 09:32:26', 770, '2010-02-08 05:33:50', 0);
INSERT INTO `comment` VALUES (95, 4, 'x7dhNIktaI', 3, 312, 9, 0, 0, '2019-05-19 15:04:46', 548, '2016-12-03 00:22:09', 0);
INSERT INTO `comment` VALUES (96, 16, 'xoLNuTIkpo', 5, 501, 18, 0, 0, '2019-04-29 16:34:34', 191, '2018-01-23 04:54:54', 0);
INSERT INTO `comment` VALUES (97, 11, '8wwJD6TdSn', 13, 122, 5, 0, 0, '2022-06-21 00:38:04', 897, '2002-03-19 13:19:05', 0);
INSERT INTO `comment` VALUES (98, 18, 'IqbYCLE4T3', 5, 856, 17, 0, 0, '2008-05-18 14:40:35', 632, '2011-02-01 12:13:01', 0);
INSERT INTO `comment` VALUES (99, 8, '76JY7gu1O9', 10, 554, 20, 0, 0, '2021-02-18 10:52:47', 233, '2019-05-11 16:55:46', 0);
INSERT INTO `comment` VALUES (100, 20, 'cgiS2rHZj3', 13, 399, 7, 0, 0, '2021-03-26 22:03:13', 1, '2021-04-30 17:02:46', 0);
INSERT INTO `comment` VALUES (1586032386376744962, -1, '我是测试评论', 1, -1, -1, 0, 0, '2022-10-29 00:29:23', 2, '2022-10-29 00:29:23', 0);
INSERT INTO `comment` VALUES (1586035286620532737, -1, '我是测试评论2', 1, -1, -1, 0, 0, '2022-10-29 00:40:54', 2, '2022-10-29 00:40:54', 0);
INSERT INTO `comment` VALUES (1601601923985715201, -1, '测试评论', 1, -1, -1, 0, 1596512301517684738, '2022-12-10 23:37:10', 1596512301517684738, '2022-12-10 23:37:10', 0);
INSERT INTO `comment` VALUES (1601602236746575873, -1, '我要测试评论', 1, -1, -1, 0, 1596512301517684738, '2022-12-10 23:38:24', 1596512301517684738, '2022-12-10 23:38:24', 0);
INSERT INTO `comment` VALUES (1601602500643794945, 1586032386376744962, '子评论1', 1, -1, 1586032386376744962, 0, 1596512301517684738, '2022-12-10 23:39:27', 1596512301517684738, '2022-12-10 23:39:27', 0);
INSERT INTO `comment` VALUES (1601602521804058625, 13, '子评论2', 1, -1, 54, 0, 1596512301517684738, '2022-12-10 23:39:32', 1596512301517684738, '2022-12-10 23:39:32', 0);
INSERT INTO `comment` VALUES (1601606443432263682, -1, '刷新测试', 1, -1, -1, 0, 1596512301517684738, '2022-12-10 23:55:07', 1596512301517684738, '2022-12-10 23:55:07', 0);
INSERT INTO `comment` VALUES (1601606499350724610, -1, '刷新测试', 1, -1, -1, 0, 1596512301517684738, '2022-12-10 23:55:21', 1596512301517684738, '2022-12-10 23:55:21', 0);
INSERT INTO `comment` VALUES (1601607876000989186, -1, '111111', 1, -1, -1, 0, 1596512301517684738, '2022-12-11 00:00:49', 1596512301517684738, '2022-12-11 00:00:49', 0);
INSERT INTO `comment` VALUES (1601608541603479553, -1, '222222', 1, -1, -1, 0, 1596512301517684738, '2022-12-11 00:03:27', 1596512301517684738, '2022-12-11 00:03:27', 0);
INSERT INTO `comment` VALUES (1601608633509068802, 13, '回复测试', 1, 1596512301517684738, 1601602521804058625, 0, 1596512301517684738, '2022-12-11 00:03:49', 1596512301517684738, '2022-12-11 00:03:49', 0);
INSERT INTO `comment` VALUES (1609567018187534338, -1, '手机评论测试', 12425, -1, -1, 0, 1609444427854626818, '2023-01-01 23:07:36', 1609444427854626818, '2023-01-01 23:07:36', 0);

-- ----------------------------
-- Table structure for link
-- ----------------------------
DROP TABLE IF EXISTS `link`;
CREATE TABLE `link`  (
  `id` bigint NOT NULL COMMENT '友链主键',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '友链名称',
  `logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '友链logo',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '友链地址',
  `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '友链描述',
  `status` tinyint NOT NULL DEFAULT 2 COMMENT '审核状态，0通过，1审核未通过，2未审核',
  `create_by` bigint NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除，1删除，0未删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `status_index`(`status` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '友链表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of link
-- ----------------------------
INSERT INTO `link` VALUES (1, '许晓明', 'https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/29/Ai.jpg', '710 North Michigan Ave', 'kiPmw7P8Yv', 0, 0, '2022-05-08 16:52:57', 0, '2023-01-01 21:06:40', 0);
INSERT INTO `link` VALUES (2, 'Fan Lu', 'R9LIKh8Ac9', 'No.816, Dongsan Road, Erxianqiao, Chenghua District', '9s87rkORzY', 0, 983, '2006-12-24 22:03:12', 1, '2022-11-19 23:01:58', 1);
INSERT INTO `link` VALUES (3, 'Liang Xiaoming', 'https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/29/133d1438a9a34d1581e9e1808f66375c.png', '130 Hanover Street', 'F7mMKJAyto', 1, 1, '2006-01-07 14:27:36', 744, '2009-01-03 02:05:23', 0);
INSERT INTO `link` VALUES (4, 'Fung Sau Man', 'https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2023/01/01/1da6154a436849de86facfae6d3b5085.jpg', '538 Narborough Rd', 'IUR3KI9GVX', 1, 1609444427854626818, '2004-07-20 22:10:19', 321, '2022-01-19 18:49:12', 0);
INSERT INTO `link` VALUES (5, 'Sit Kwok Ming', 'zpHH8KMVQp', '335 Dong Zhi Men, Dongcheng District', 'kJS8Lf0rP0', 0, 584, '2022-07-16 05:12:49', 1, '2022-11-19 22:57:48', 1);

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` bigint NOT NULL COMMENT '主键',
  `pid` bigint NOT NULL DEFAULT 0 COMMENT '父权限id',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  `permission_value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限值',
  `type` tinyint NOT NULL DEFAULT 0 COMMENT '菜单类型（0：目录，1：菜单，2：按钮）',
  `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由访问路径',
  `component` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'vue组件路径',
  `redirect` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '重定向地址',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图标',
  `hidden` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否隐藏路由1（true）隐藏，0（false）不隐藏',
  `is_enable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否启用（1：启用，0：禁止）',
  `sort` int NOT NULL DEFAULT 0 COMMENT '菜单排序',
  `create_by` bigint NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除，1删除，0未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1594330305295142914, 0, '仪表盘', 'dashboard', 1, 'dashboard', 'dashboard/index', '', 'dashboard', 0, 1, 0, 1, '2022-11-20 22:02:21', 0, '2022-11-27 01:20:39', 0);
INSERT INTO `menu` VALUES (1594333233011609601, 0, '权限管理', NULL, 0, 'permission', NULL, '/permission/user', 'eye-open', 0, 1, 2, 1, '2022-11-20 22:13:59', 0, '2022-11-27 01:12:29', 0);
INSERT INTO `menu` VALUES (1594731905759145986, 1594330305295142914, '查看统计数据', 'dashboard::statistics', 2, NULL, NULL, NULL, NULL, 0, 1, 0, 1, '2022-11-22 00:38:10', 1, '2022-11-22 00:38:10', 0);
INSERT INTO `menu` VALUES (1595439887010529281, 1594333233011609601, '用户管理', 'permission::user::manage', 1, 'permission/user', 'permission/user/index', '', 'user-solid-circle', 0, 1, 0, 0, '2022-11-23 23:31:26', 0, '2022-11-27 01:38:01', 0);
INSERT INTO `menu` VALUES (1595440445826039809, 1594333233011609601, '角色管理', 'permission::role::manage', 1, 'permission/role', 'permission/role/index', '', 'role-management', 0, 1, 1, 0, '2022-11-23 23:33:39', 0, '2022-11-27 01:11:41', 0);
INSERT INTO `menu` VALUES (1595440728740233217, 1594333233011609601, '菜单管理', 'permission::menu::manage', 1, 'permission/menu', 'permission/menu/index', '', 'menu', 0, 1, 2, 0, '2022-11-23 23:34:46', 0, '2022-11-27 01:11:45', 0);
INSERT INTO `menu` VALUES (1595441480292401154, 0, '博客管理', 'article::manage', 1, 'article/management', 'article/management', '', 'article-management', 0, 1, 3, 0, '2022-11-23 23:37:45', 0, '2022-11-27 01:38:39', 0);
INSERT INTO `menu` VALUES (1595441638254084098, 0, '标签管理', 'tag::manage', 1, 'tag/management', 'tag/management', '', 'tag', 0, 1, 4, 0, '2022-11-23 23:38:23', 0, '2022-11-27 01:38:27', 0);
INSERT INTO `menu` VALUES (1595445120390430722, 0, '评论管理', 'comment::manage', 1, 'comment/management', 'comment/management', '', 'comments', 0, 1, 5, 0, '2022-11-23 23:52:13', 0, '2022-11-27 01:38:46', 0);
INSERT INTO `menu` VALUES (1595445225172533249, 0, '友链管理', 'link::manage', 1, 'link/management', 'link/management', '', 'link', 0, 1, 6, 0, '2022-11-23 23:52:38', 0, '2022-11-27 01:38:55', 0);
INSERT INTO `menu` VALUES (1595445781735702530, 0, '写博客', 'article::write', 1, 'blog/write', 'article/write', '', 'write', 0, 1, 1, 0, '2022-11-23 23:54:51', 0, '2022-11-30 22:35:00', 0);
INSERT INTO `menu` VALUES (1595780777856000002, 1595440728740233217, '菜单查询', 'permission::menu::manage::get', 2, NULL, NULL, NULL, NULL, 0, 1, 0, 0, '2022-11-24 22:06:00', 0, '2022-11-24 22:06:00', 0);
INSERT INTO `menu` VALUES (1595781066969374721, 1595440728740233217, '菜单添加', 'permission::menu::manage::add', 2, NULL, NULL, NULL, NULL, 0, 1, 1, 0, '2022-11-24 22:07:09', 0, '2022-11-24 22:07:09', 0);
INSERT INTO `menu` VALUES (1595781136624181249, 1595440728740233217, '菜单修改', 'permission::menu::manage::update', 2, NULL, NULL, NULL, NULL, 0, 1, 2, 0, '2022-11-24 22:07:26', 0, '2022-11-24 22:07:26', 0);
INSERT INTO `menu` VALUES (1595781196149743617, 1595440728740233217, '菜单删除', 'permission::menu::manage::delete', 2, NULL, NULL, NULL, NULL, 0, 1, 3, 0, '2022-11-24 22:07:40', 0, '2022-11-24 22:07:40', 0);
INSERT INTO `menu` VALUES (1595781361526956033, 1595440445826039809, '角色查询', 'permission::role::manage::get', 2, NULL, NULL, NULL, NULL, 0, 1, 0, 0, '2022-11-24 22:08:19', 0, '2022-11-24 22:08:19', 0);
INSERT INTO `menu` VALUES (1595781418078756865, 1595440445826039809, '角色添加', 'permission::role::manage::add', 2, NULL, NULL, NULL, NULL, 0, 1, 1, 0, '2022-11-24 22:08:33', 0, '2022-11-24 22:08:33', 0);
INSERT INTO `menu` VALUES (1595781464769748994, 1595440445826039809, '角色修改', 'permission::role::manage::update', 2, NULL, NULL, NULL, NULL, 0, 1, 0, 0, '2022-11-24 22:08:44', 0, '2022-11-24 22:08:44', 0);
INSERT INTO `menu` VALUES (1595781503361540098, 1595440445826039809, '角色删除', 'permission::role::manage::delete', 2, NULL, NULL, NULL, NULL, 0, 1, 0, 0, '2022-11-24 22:08:53', 0, '2022-11-24 22:08:53', 0);
INSERT INTO `menu` VALUES (1595781779430629378, 1595439887010529281, '用户查询', 'permission::user::manage::get', 2, NULL, NULL, NULL, NULL, 0, 1, 0, 0, '2022-11-24 22:09:59', 0, '2022-11-24 22:09:59', 0);
INSERT INTO `menu` VALUES (1595781856660348930, 1595439887010529281, '用户添加', 'permission::user::manage::add', 2, NULL, NULL, NULL, NULL, 0, 1, 1, 0, '2022-11-24 22:10:17', 0, '2022-11-24 22:10:17', 0);
INSERT INTO `menu` VALUES (1595782260164976642, 1595439887010529281, '用户角色分配', 'permission::user::manage::assign_role', 2, NULL, NULL, NULL, NULL, 0, 1, 3, 0, '2022-11-24 22:11:54', 0, '2022-11-27 16:43:15', 0);
INSERT INTO `menu` VALUES (1596786142743732226, 1595439887010529281, '用户删除', 'permission::user::manage::delete', 2, NULL, NULL, NULL, NULL, 0, 1, 4, 0, '2022-11-27 16:40:58', 0, '2022-11-27 16:43:18', 0);
INSERT INTO `menu` VALUES (1596786201115860994, 1595439887010529281, '用户批量删除', 'permission::user::manage::batchDelete', 2, NULL, NULL, NULL, NULL, 0, 1, 5, 0, '2022-11-27 16:41:12', 0, '2022-11-27 16:43:21', 0);
INSERT INTO `menu` VALUES (1596786845700694017, 1595439887010529281, '用户修改', 'permission::user::manage::update', 2, NULL, NULL, NULL, NULL, 0, 1, 2, 0, '2022-11-27 16:43:46', 0, '2022-11-27 16:43:46', 0);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint NOT NULL COMMENT '角色id',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `key` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色权限字符串',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色备注',
  `sort` int NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `is_enable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否启用，1启用，0禁用',
  `create_by` bigint NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint NOT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除，1删除，0未删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name_idx`(`name` ASC) USING BTREE COMMENT '角色名唯一索引',
  UNIQUE INDEX `key_idx`(`key` ASC) USING BTREE COMMENT '权限字符串唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (2, 'Che Wing Sze', 'C49CpWf2KK', '6EDWLseU7L', 0, 1, 754, '2019-07-29 21:32:11', 0, '2022-11-26 22:10:54', 1);
INSERT INTO `role` VALUES (4, 'Yin Wai Man', 'aNhu1SgSF9', 's0jADhQSuK', 948, 1, 528, '2019-07-09 18:57:31', 126, '2010-09-06 03:40:46', 1);
INSERT INTO `role` VALUES (1596578118036914178, '友链管理员', 'link', '管理友链', 0, 1, 0, '2022-11-27 02:54:21', 0, '2022-11-27 02:54:21', 0);

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `id` bigint NOT NULL COMMENT '主键',
  `role_id` bigint NOT NULL COMMENT '角色id',
  `menu_id` bigint NOT NULL COMMENT '菜单id',
  `create_by` bigint NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `role_menu_id_idx`(`role_id` ASC, `menu_id` ASC) USING BTREE COMMENT '角色、菜单id索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (1596787381397200897, 1596578118036914178, 1594333233011609601, 0, '2022-11-27 16:45:53', 0, '2022-11-27 16:45:53');
INSERT INTO `role_menu` VALUES (1596787381397200898, 1596578118036914178, 1595439887010529281, 0, '2022-11-27 16:45:53', 0, '2022-11-27 16:45:53');
INSERT INTO `role_menu` VALUES (1596787381397200899, 1596578118036914178, 1594330305295142914, 0, '2022-11-27 16:45:53', 0, '2022-11-27 16:45:53');
INSERT INTO `role_menu` VALUES (1596787381397200900, 1596578118036914178, 1594731905759145986, 0, '2022-11-27 16:45:53', 0, '2022-11-27 16:45:53');
INSERT INTO `role_menu` VALUES (1596787381397200901, 1596578118036914178, 1595781779430629378, 0, '2022-11-27 16:45:53', 0, '2022-11-27 16:45:53');
INSERT INTO `role_menu` VALUES (1596787381397200902, 1596578118036914178, 1595445225172533249, 0, '2022-11-27 16:45:53', 0, '2022-11-27 16:45:53');

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `id` bigint NOT NULL COMMENT 'tag的Id',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'tag名称',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'tag备注描述',
  `is_enable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否启用（1：启用，0：禁止）',
  `create_by` bigint NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除，1删除，0未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '标签表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES (1591823685721903105, 'Java', 'JavaSE技术', 1, 1, '2022-11-14 00:01:56', 0, '2022-11-27 23:55:57', 0);
INSERT INTO `tag` VALUES (1597962703857598466, '学习', '学习过程中的一些记录', 1, 0, '2022-11-30 22:36:12', 0, '2022-11-30 22:36:12', 0);
INSERT INTO `tag` VALUES (1599056801603575809, 'JavaWeb', 'JavaWeb技术', 1, 0, '2022-12-03 23:03:45', 0, '2022-12-03 23:03:45', 0);
INSERT INTO `tag` VALUES (1599067611436474370, 'Redis', 'redis是一个高性能的key-value数据库', 1, 0, '2022-12-03 23:46:42', 0, '2022-12-03 23:46:42', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL COMMENT '用户id',
  `account` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户账号',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `nickname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '邮箱',
  `phone` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话号码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'https://avatars.githubusercontent.com/u/58805009?s=400&u=162a7d6757711b756f513ca36aa3576277db0ba1&v=4' COMMENT '头像地址',
  `type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '用户类型，0为普通用户，1为管理员',
  `is_enable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否启用，1启用，0禁用',
  `create_by` bigint NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint NOT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除，1删除，0未删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `account_index`(`account` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (0, 'admin', '$2a$10$gbLEnOIVfZSS8F9et4ie0.rGKmGQnbzQzmFd2pJZHlg/gTL89E/im', 'admin', 'admin@qq.com', '100', 'https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/29/133d1438a9a34d1581e9e1808f66375c.png', 1, 1, 1, '2022-10-27 22:13:59', 1, '2022-10-27 22:13:59', 0);
INSERT INTO `user` VALUES (1, 'user', '$2a$10$gbLEnOIVfZSS8F9et4ie0.rGKmGQnbzQzmFd2pJZHlg/gTL89E/im', 'user', 'user@test.com', '110', 'https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/29/Ai.jpg', 0, 1, 1, '2022-10-27 22:17:05', 1, '2022-11-22 00:28:34', 0);
INSERT INTO `user` VALUES (1596512301517684738, 'nichijoux', '$2a$10$gbLEnOIVfZSS8F9et4ie0.rGKmGQnbzQzmFd2pJZHlg/gTL89E/im', 'nichijoux', 'nichijoux@outlook.com', '120', 'https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2022/11/29/Ai.jpg', 1, 1, 0, '2022-11-26 22:32:49', 0, '2022-11-30 22:34:37', 0);
INSERT INTO `user` VALUES (1609444427854626818, 'testuser', '$2a$10$gbLEnOIVfZSS8F9et4ie0.rGKmGQnbzQzmFd2pJZHlg/gTL89E/im', 'testuser', 'testuser@qq.com', NULL, 'https://nichijoux-blog.oss-cn-chengdu.aliyuncs.com/2023/01/01/1da6154a436849de86facfae6d3b5085.jpg', 0, 1, -1, '2023-01-01 15:00:28', 1609444427854626818, '2023-01-01 17:50:48', 0);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `role_id` bigint NOT NULL COMMENT '角色id',
  `create_by` bigint NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_role_id_idx`(`user_id` ASC, `role_id` ASC) USING BTREE COMMENT '用户id、角色id索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1596578167450009601, 1596512301517684738, 1596578118036914178, 0, '2022-11-27 02:54:33', 0, '2022-11-27 02:54:33');

SET FOREIGN_KEY_CHECKS = 1;
