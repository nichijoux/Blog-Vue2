<template>
  <div class="app-container">
    <!--查询表单开始-->
    <el-card class="operate-container" shadow="never" align="center">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="友链名">
          <el-input v-model="queryCondition.name" placeholder="友链名" />
        </el-form-item>

        <el-form-item label="友链状态">
          <el-select v-model="queryCondition.status" placeholder="未审核">
            <el-option label="通过" :value="0" />
            <el-option label="未通过" :value="1" />
            <el-option label="未审核" :value="2" />
          </el-select>
        </el-form-item>

        <!-- 查询表单操作开始 -->
        <div>
          <el-button type="primary" icon="el-icon-search" @click="getLinkList()"
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

        <el-divider></el-divider>

        <div class="m-center">
          <el-button
            type="success"
            class="user-button"
            @click="openLinkDialog()"
            >添加友链</el-button
          >
        </div>
      </el-form>
    </el-card>
    <!-- 查询表单结束 -->

    <el-divider></el-divider>

    <!-- 数据表格开始 -->
    <el-table :data="linkList" border stripe>
      <!-- id列开始 -->
      <el-table-column prop="id" label="id" align="center" />
      <!-- id列结束 -->

      <!-- 友链名开始 -->
      <el-table-column label="友链名" align="center">
        <template slot-scope="scope">
          <el-tag size="medium">{{ scope.row.name }}</el-tag>
        </template>
      </el-table-column>
      <!-- 友链名结束 -->

      <!-- 友链logo开始 -->
      <el-table-column prop="logo" label="友链logo" width="150" align="center">
        <!-- 动态加载url -->
        <template slot-scope="scope">
          <img
            :src="scope.row.logo"
            width="130"
            height="130"
            class="el-image__inner el-image__preview"
          />
        </template>
      </el-table-column>
      <!-- 友链logo结束 -->

      <!-- 友链地址开始 -->
      <el-table-column prop="address" label="友链地址" align="center" />
      <!-- 友链地址结束 -->

      <!-- 友链描述开始 -->
      <el-table-column prop="description" label="友链描述" align="center" />
      <!-- 友链描述结束 -->

      <!-- 友链状态开始 -->
      <el-table-column prop="status" label="友链状态" align="center">
        <template slot-scope="scope">
          <el-tag size="medium" type="success" v-if="scope.row.status == 0"
            >通过</el-tag
          >
          <el-tag size="medium" type="warning" v-if="scope.row.status == 1"
            >未通过</el-tag
          >
          <el-tag size="medium" type="danger" v-if="scope.row.status == 2"
            >未审核</el-tag
          >
        </template>
      </el-table-column>
      <!-- 友链状态结束 -->

      <!-- 对友链修改、删除开始 -->
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button
            type="primary"
            icon="el-icon-edit"
            @click="openLinkDialog(scope.row)"
            size="small"
            round
            >修改</el-button
          >
          <el-button
            type="danger"
            icon="el-icon-delete"
            @click="deleteLink(scope.row.id, scope.row.name)"
            size="small"
            round
            >删除</el-button
          >
        </template>
      </el-table-column>
      <!-- 对友链修改、删除结束 -->
    </el-table>
    <!-- 数据表格结束 -->

    <!-- 添加/修改友链的对话框开始 -->
    <el-dialog
      :visible.sync="linkDialogVisible"
      :title="linkDialogTitle"
      append-to-body
    >
      <el-form
        :model="linkDialogData"
        :rules="linkDialogRules"
        ref="linkDialogForm"
        label-width="120px"
      >
        <!-- 友链名 -->
        <el-form-item label="友链名" prop="name">
          <el-input v-model="linkDialogData.name" placeholder="请输入友链名" />
        </el-form-item>
        <!-- 友链描述 -->
        <el-form-item label="友链描述" prop="description">
          <el-input
            v-model="linkDialogData.description"
            type="textarea"
            placeholder="请输入友链描述"
          />
        </el-form-item>
        <!-- 友链logo -->
        <el-form-item label="友链logo" prop="logo">
          <el-input
            v-model="linkDialogData.logo"
            placeholder="请输入友链logo地址"
          />
        </el-form-item>
        <!-- 友链地址 -->
        <el-form-item label="友链地址" prop="address">
          <el-input
            v-model="linkDialogData.address"
            placeholder="请输入友链地址"
          />
        </el-form-item>
        <!-- 友链状态 -->
        <el-form-item label="友链状态" prop="status">
          <el-select v-model="linkDialogData.status" placeholder="请选择">
            <el-option :key="0" label="审核通过" :value="0" />
            <el-option :key="1" label="审核未通过" :value="1" />
            <el-option :key="2" label="未审核" :value="2" />
          </el-select>
        </el-form-item>
      </el-form>
      <!-- 保存和取消的按钮 -->
      <div slot="footer" class="dialog-footer m-center">
        <el-button type="primary" @click="addOrUpdateLink()">确定</el-button>
        <el-button type="warning" @click="cancelLinkDialog()">取消</el-button>
      </div>
    </el-dialog>
    <!-- 添加/修改友链的对话框结束 -->

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
import linkAPI from "@/api/link";

const linkFormData = {
  name: "",
  description: "",
  logo: "",
  address: "",
  status: 0,
};

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
      // 友链列表
      linkList: [],
      // 查询条件
      queryCondition: {},

      // 对话框是否可见
      linkDialogVisible: false,
      // 对话框标题
      linkDialogTitle: "",
      // 对话框数据
      linkDialogData: { ...linkFormData },
      // 对话框规则
      linkDialogRules: {
        name: [{ required: true, message: "请输入友链名", trigger: "blur" }],
        description: [
          { required: true, message: "请输入友链描述", trigger: "blur" },
          { min: 3, message: "备注至少3个字符", trigger: "blur" },
        ],
        logo: [{ required: true, message: "请输入友链logo", trigger: "blur" }],
        address: [
          { required: true, message: "请输入友链地址", trigger: "blur" },
        ],
      },
    };
  },
  // 渲染前执行
  created() {
    // 查询
    this.getLinkList();
  },
  // 方法区
  methods: {
    // 获取友链列表
    getLinkList(index = 1) {
      this.index = index;
      // API查询
      linkAPI
        .pageQueryLink(this.index, this.limit, this.queryCondition)
        .then((response) => {
          this.linkList = response.data.records;
          this.total = Number(response.data.total);
        });
    },
    // 删除友链
    deleteLink(linkId, linkName) {
      this.$confirm(`确定要删除友链[ ${linkName} ]吗?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        // 调用接口删除
        linkAPI.deleteLink(linkId).then((response) => {
          // 提示
          this.$notify.success({
            title: "系统提示",
            message: "删除成功",
            duration: 1500,
          });
          // 刷新
          this.getLinkList();
        });
      });
    },
    // 每页记录数改变
    handleSizeChange(limit) {
      this.limit = limit;
      this.getLinkList();
    },
    // 当前页改变
    handleCurrentChange(index) {
      this.getLinkList(index);
    },
    // 重置查询条件
    resetQueryCondition() {
      this.queryCondition = {};
      this.getLinkList();
    },

    // 打开对话框
    openLinkDialog(link) {
      // 打开对话框
      this.linkDialogVisible = true;
      // 传递数据
      if (link) {
        this.linkDialogData = { ...link };
        this.linkDialogTitle = "编辑友链";
      } else {
        this.linkDialogData = { ...linkFormData };
        this.linkDialogTitle = "添加友链";
      }
    },
    // 更新友链
    updateLink(tag) {
      linkAPI
        .updateLink(tag)
        .then((response) => {
          this.$message.success("更新友链成功");
          // 刷新数据
          this.getLinkList();
          // 关闭对话框
          this.cancelLinkDialog();
        })
        .catch((error) => {
          this.$message.error("更新友链失败");
          // 关闭对话框
          this.cancelLinkDialog();
        });
    },
    // 添加友链
    addLink(link) {
      linkAPI
        .addLink(link)
        .then((response) => {
          this.$message.success("添加友链成功");
          // 刷新数据
          this.getLinkList();
          // 关闭对话框
          this.cancelLinkDialog();
        })
        .catch((error) => {
          this.$message.error("添加友链失败");
          // 关闭对话框
          this.cancelLinkDialog();
        });
    },
    // 添加或者更新友链
    addOrUpdateLink() {
      this.$refs.linkDialogForm.validate((valid) => {
        if (valid) {
          if (this.linkDialogData.id) {
            // 更新友链数据
            this.updateLink(this.linkDialogData);
          } else {
            // 添加友链数据
            this.addLink(this.linkDialogData);
          }
        } else {
          this.$message.warning("表单数据不合法");
        }
      });
    },
    // 关闭对话框
    cancelLinkDialog() {
      this.linkDialogVisible = false;
      this.linkDialogData = { ...linkFormData };
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
