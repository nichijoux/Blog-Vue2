<template>
  <div class="app-container">
    <!--查询表单开始-->
    <el-card class="operate-container" shadow="never" align="center">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="标题名">
          <el-input v-model="queryCondition.title" placeholder="标题名" />
        </el-form-item>

        <el-form-item label="发表状态">
          <el-select
            v-model="queryCondition.status"
            clearable
            placeholder="已发表"
          >
            <el-option :value="true" label="已发表" />
            <el-option :value="false" label="未发表" />
          </el-select>
        </el-form-item>

        <el-divider></el-divider>

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

        <el-form-item label="更新时间">
          <el-date-picker
            v-model="updateDateRange"
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
            @click="getArticleList()"
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
    <el-table :data="articleList" border stripe>
      <!-- 序号列开始 -->
      <el-table-column label="#" width="40" align="center">
        <template slot-scope="scope">
          {{ (index - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <!-- 序号列结束 -->

      <el-table-column prop="title" label="标题名" align="center" />

      <el-table-column prop="summary" label="文章摘要" />

      <!-- 封面列开始 -->
      <el-table-column prop="cover" label="封面" width="150" align="center">
        <!-- 动态加载url -->
        <template slot-scope="scope">
          <img
            :src="scope.row.cover"
            width="130"
            height="130"
            class="el-image__inner el-image__preview"
          />
        </template>
      </el-table-column>
      <!-- 封面列结束 -->

      <!-- 发表状态列开始 -->
      <el-table-column label="发表状态" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status == 1" type="success" size="medium"
            >已发表</el-tag
          >
          <el-tag v-if="scope.row.status == 0" size="medium">未发表</el-tag>
        </template>
      </el-table-column>
      <!-- 发表状态列结束 -->

      <!-- 标签列开始 -->
      <el-table-column label="标签" align="center">
        <template slot-scope="scope">
          <el-tag
            v-for="(tagName, tagId) in scope.row.tagMap"
            size="medium"
            :key="tagId"
            :class="tagBackground[tagId % 10]"
          >
            {{ tagName }}
          </el-tag>
        </template>
      </el-table-column>
      <!-- 标签列结束 -->

      <!-- 创建时间列开始 -->
      <el-table-column prop="createTime" label="创建时间" align="center" />
      <!-- 创建时间列结束 -->

      <!-- 更新时间列开始 -->
      <el-table-column prop="updateTime" label="更新时间" align="center" />
      <!-- 更新时间列结束 -->

      <!-- 是否允许评论开始 -->
      <el-table-column label="是否允许评论" width="150" align="center">
        <template slot-scope="scope">
          <el-switch
            style="display: block"
            v-model="scope.row.commentable"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-text="启用"
            inactive-text="禁用"
            @change="commentableSwitch(scope.row.id, scope.row.title, $event)"
          ></el-switch>
        </template>
      </el-table-column>
      <!-- 是否允许评论结束 -->

      <!-- 对文章修改、删除开始 -->
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <router-link :to="'/blog/write?id=' + scope.row.id">
            <el-button type="primary" icon="el-icon-edit" size="small" round
              >修改</el-button
            >
          </router-link>

          <el-button
            type="danger"
            icon="el-icon-delete"
            @click="deleteArticle(scope.row.id, scope.row.title)"
            size="small"
            round
            >删除</el-button
          >
        </template>
      </el-table-column>
      <!-- 对文章修改、删除结束 -->
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
import articleAPI from "@/api/article";

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
      // 文章列表
      articleList: [],
      // 查询条件
      queryCondition: {},
      // 时间范围
      createDateRange: [],
      updateDateRange: [],
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
      // tag颜色class
      tagBackground: [
        "tagBG1",
        "tagBG2",
        "tagBG3",
        "tagBG4",
        "tagBG5",
        "tagBG6",
        "tagBG7",
        "tagBG8",
        "tagBG9",
        "tagBG10",
      ],
    };
  },
  // 渲染前执行
  created() {
    // 查询
    this.getArticleList();
  },
  // 方法区
  methods: {
    // 获取文章列表
    getArticleList(index = 1) {
      this.index = index;
      // 创建时间
      if (this.createDateRange == null) {
        this.createDateRange = [];
      }
      if (this.createDateRange.length > 0) {
        this.queryCondition.createBeginTime = this.createDateRange[0];
        this.queryCondition.createEndTime = this.createDateRange[1];
      }
      // 更新时间
      if (this.updateDateRange == null) {
        this.updateDateRange = [];
      }
      if (this.updateDateRange.length > 0) {
        this.queryCondition.updateBeginTime = this.updateDateRange[0];
        this.queryCondition.updateEndTime = this.updateDateRange[1];
      }
      // API查询
      articleAPI
        .pageQueryArticle(this.index, this.limit, this.queryCondition)
        .then((response) => {
          this.articleList = response.data.records;
          this.total = Number(response.data.total);
        });
    },
    // 删除文章
    deleteArticle(articleId, articleTitle) {
      this.$confirm(`确定要删除[ ${articleTitle} ]吗?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        // 调用接口删除
        articleAPI.deleteArticle(articleId).then((response) => {
          // 提示
          const messageBox = this.$createElement;
          this.$notify({
            message: messageBox("i", { style: "color: teal" }, "删除成功"),
            duration: 1500,
          });
          // 刷新
          this.getArticleList();
        });
      });
    },
    // 是否允许评论改变
    commentableSwitch(articleId, articleTitle) {},
    // 每页记录数改变
    handleSizeChange(limit) {
      this.limit = limit;
      this.getArticleList();
    },
    // 当前页改变
    handleCurrentChange(index) {
      this.getArticleList(index);
    },
    // 重置查询条件
    resetQueryCondition() {
      this.queryCondition = {};
      this.createDateRange = [];
      this.updateDateRange = [];
      this.getArticleList();
    },
  },
};
</script>

<style scoped>
.tagBG1 {
  background: #fff0f5;
}
.tagBG2 {
  background: #f0f8ff;
}
.tagBG3 {
  background: #e6e6fa;
}
.tagBG4 {
  background: #ffefd5;
}
.tagBG5 {
  background: #faf0e6;
}
.tagBG6 {
  background: #fae3e6;
}
.tagBG7 {
  background: #e6fafa;
}
.tagBG8 {
  background: #f8fae6;
}
.tagBG9 {
  background: #fae6f1;
}
.tagBG10 {
  background: #dba670;
}
</style>
