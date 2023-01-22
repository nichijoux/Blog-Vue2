<template>
  <div class="app-container">
    <!--查询表单开始-->
    <el-card class="operate-container" shadow="never" align="center">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="文章标题">
          <el-input
            v-model="queryCondition.articleTitle"
            placeholder="文章标题"
          />
        </el-form-item>

        <el-form-item label="用户账号">
          <el-input v-model="queryCondition.account" placeholder="用户账号" />
        </el-form-item>

        <el-form-item label="评论类型">
          <el-select v-model="queryCondition.type" placeholder="普通评论">
            <el-option label="普通评论" :value="0" />
            <el-option label="友链评论" :value="1" />
          </el-select>
        </el-form-item>

        <el-form-item label="创建时间">
          <el-date-picker
            v-model="createDateRange"
            type="datetimerange"
            align="right"
            unlink-panels
            range-separator="至"
            start-placeholder="开始月份"
            end-placeholder="结束月份"
            :picker-options="pickerOptions"
            value-format="yyyy-MM-dd HH:mm:ss"
            clearable
          >
          </el-date-picker>
        </el-form-item>

        <!-- 查询表单操作开始 -->
        <div>
          <el-button
            type="primary"
            icon="el-icon-search"
            @click="getCommentList()"
            >查询</el-button
          >
          <el-button
            type="warning"
            icon="el-icon-refresh-left"
            @click="resetQueryCondition()"
            >清空</el-button
          >
        </div>
        <!-- 查询表单操作结束 -->
      </el-form>
    </el-card>
    <!-- 查询表单结束 -->

    <el-divider></el-divider>

    <!-- 数据表格开始 -->
    <el-table :data="commentList" border stripe>
      <!-- 序号列开始 -->
      <el-table-column prop="id" label="id" align="center" />
      <!-- 序号列结束 -->

      <!-- 评论类型开始 -->
      <el-table-column label="评论类型" align="center">
        <template slot-scope="scope">
          <el-tag size="medium" type="success" v-if="scope.row.articleId != -1"
            >普通评论</el-tag
          >
          <el-tag size="medium" type="danger" v-else>友链评论</el-tag>
        </template>
      </el-table-column>
      <!-- 评论类型结束 -->

      <!-- 用户账号开始 -->
      <el-table-column label="用户账号" align="center">
        <template slot-scope="scope">
          <el-tag size="medium" type="warning">{{ scope.row.account }}</el-tag>
        </template>
      </el-table-column>
      <!-- 用户账号结束 -->

      <!-- 评论文章标题开始 -->
      <el-table-column label="文章标题" align="center">
        <template slot-scope="scope">
          <el-tag size="medium">{{ scope.row.articleTitle }}</el-tag>
        </template>
      </el-table-column>
      <!-- 评论文章标题结束 -->

      <!-- 评论内容开始 -->
      <el-table-column prop="content" label="评论内容" align="center" />
      <!-- 评论内容结束 -->

      <!-- 评论内容开始 -->
      <el-table-column prop="toCommentId" label="目标评论id" align="center" />
      <!-- 评论内容结束 -->

      <!-- 创建时间列开始 -->
      <el-table-column prop="createTime" label="评论时间" align="center" />
      <!-- 创建时间列结束 -->

      <!-- 对标签修改、删除开始 -->
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button
            type="danger"
            icon="el-icon-delete"
            @click="deleteTag(scope.row.id, scope.row.name)"
            size="small"
            round
            >删除</el-button
          >
        </template>
      </el-table-column>
      <!-- 对标签修改、删除结束 -->
    </el-table>
    <!-- 数据表格结束 -->

    <!-- 分页插件开始 -->
    <el-pagination
      :current-page="index"
      :page-size="limit"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :page-sizes="[5, 10, 20, 30, 40, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper"
      style="padding: 30px 0; text-align: center"
    >
    </el-pagination>
    <!-- 分页插件结束 -->

    <!-- 快速回到顶部开始 -->
    <el-backtop></el-backtop>
    <!-- 快速回到顶部结束 -->
  </div>
</template>

<script>
import commentAPI from "@/api/comment";

export default {
  // 数据区
  data() {
    return {
      // 当前页
      index: 1,
      // 每页记录数
      limit: 10,
      // 总记录条数
      total: 0,
      // 评论列表
      commentList: [],
      // 查询条件
      queryCondition: {},
      // 时间范围
      createDateRange: [],
      // 时间选择器的快速选择
      pickerOptions: {
        shortcuts: [
          {
            text: "最近一周",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            text: "最近一个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            text: "最近三个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit("pick", [start, end]);
            },
          },
        ],
      },
    };
  },
  // 渲染前执行
  created() {
    // 查询
    this.getCommentList();
  },
  // 方法区
  methods: {
    // 获取评论列表
    getCommentList(index = 1) {
      this.index = index;
      // 创建时间
      if (this.createDateRange == null) {
        this.createDateRange = [];
      }
      if (this.createDateRange.length > 0) {
        this.queryCondition.createBeginTime = this.createDateRange[0];
        this.queryCondition.createEndTime = this.createDateRange[1];
      }
      commentAPI
        .pageQueryComment(this.index, this.limit, this.queryCondition)
        .then((response) => {
          this.commentList = response.data.records;
          this.total = Number(response.data.total);
        });
    },
    // 删除评论
    deleteComment(commentId, commentName) {
      this.$confirm(`确定要删除[ ${commentName} ]吗?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        // 调用接口删除
        commentAPI.deleteComment(commentId).then((response) => {
          // 提示
          const messageBox = this.$createElement;
          this.$notify.success({
            title: "系统提示",
            message: "删除成功",
            duration: 1500,
          });
          // 刷新
          this.getCommentList();
        });
      });
    },
    // 每页记录数改变
    handleSizeChange(limit) {
      this.limit = limit;
      this.getCommentList();
    },
    // 当前页改变
    handleCurrentChange(index) {
      this.getCommentList(index);
    },
    // 重置查询条件
    resetQueryCondition() {
      this.queryCondition = {};
      this.createDateRange = [];
      this.getCommentList();
    },
  },
};
</script>

<style scoped>
.m-center {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
