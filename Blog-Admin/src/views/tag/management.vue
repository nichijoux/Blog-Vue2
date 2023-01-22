<template>
  <div class="app-container">
    <!--查询表单开始-->
    <el-card class="operate-container" shadow="never" align="center">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="标签名">
          <el-input v-model="queryCondition.name" placeholder="标签名" />
        </el-form-item>

        <el-form-item label="标签备注">
          <el-input v-model="queryCondition.remark" placeholder="标签备注" />
        </el-form-item>

        <el-form-item label="是否启用">
          <el-select
            v-model="queryCondition.enable"
            clearable
            placeholder="启用"
          >
            <el-option :value="true" label="启用" />
            <el-option :value="false" label="禁用" />
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
          <el-button type="primary" icon="el-icon-search" @click="getTagList()"
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
          <el-button type="success" class="user-button" @click="openTagDialog()"
            >添加标签</el-button
          >
        </div>
      </el-form>
    </el-card>
    <!-- 查询表单结束 -->

    <el-divider></el-divider>

    <!-- 数据表格开始 -->
    <el-table :data="tagList" border stripe>
      <!-- 序号列开始 -->
      <el-table-column label="#" width="40" align="center">
        <template slot-scope="scope">
          {{ (index - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <!-- 序号列结束 -->

      <el-table-column label="标签名" align="center">
        <template slot-scope="scope">
          <el-tag size="medium">{{ scope.row.name }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="标签备注" align="center">
        <template slot-scope="scope">
          <el-tag size="medium" type="warning">{{ scope.row.remark }}</el-tag>
        </template>
      </el-table-column>

      <!-- 是否启用开始 -->
      <el-table-column label="是否启用" width="100" align="center">
        <template slot-scope="scope">
          <el-switch
            style="display: block"
            v-model="scope.row.enable"
            active-color="#13ce66"
            inactive-color="#ff4949"
            @change="enableOrDisableTag(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <!-- 是否启用结束 -->

      <!-- 创建时间列开始 -->
      <el-table-column prop="createTime" label="创建时间" align="center" />
      <!-- 创建时间列结束 -->

      <!-- 更新时间列开始 -->
      <el-table-column prop="updateTime" label="更新时间" align="center" />
      <!-- 更新时间列结束 -->

      <!-- 对标签修改、删除开始 -->
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button
            type="primary"
            icon="el-icon-edit"
            @click="openTagDialog(scope.row)"
            size="small"
            round
            >修改</el-button
          >

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

    <!-- 添加/修改标签的对话框开始 -->
    <el-dialog
      :visible.sync="tagDialogVisible"
      :title="tagDialogTitle"
      append-to-body
    >
      <el-form
        :model="tagDialogData"
        :rules="tagDialogRules"
        ref="tagDialogForm"
        label-width="120px"
      >
        <!-- 标签名 -->
        <el-form-item label="标签名" prop="name">
          <el-input v-model="tagDialogData.name" />
        </el-form-item>
        <!-- 标签备注 -->
        <el-form-item label="备注" prop="remark">
          <el-input v-model="tagDialogData.remark" />
        </el-form-item>
        <!-- 是否启用 -->
        <el-form-item label="是否启用" prop="enable">
          <el-radio-group v-model="tagDialogData.enable">
            <el-radio :key="true" :label="true">启用</el-radio>
            <el-radio :key="false" :label="false">停用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <!-- 保存和取消的按钮 -->
      <div slot="footer" class="dialog-footer m-center">
        <el-button type="primary" @click="addOrUpdateTag()">确定</el-button>
        <el-button type="warning" @click="cancelTagDialog()">取消</el-button>
      </div>
    </el-dialog>
    <!-- 添加/修改标签的对话框结束 -->

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
import tagAPI from "@/api/tag";

const tagFormData = {
  // 标签名
  name: "",
  // 备注
  remark: "",
  // 是否启用
  enable: true,
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
      // 标签列表
      tagList: [],
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
      // 对话框是否可见
      tagDialogVisible: false,
      // 对话框标题
      tagDialogTitle: "",
      // 对话框数据
      tagDialogData: { ...tagFormData },
      // 对话框规则
      tagDialogRules: {
        name: [{ required: true, message: "请输入标签名", trigger: "blur" }],
        remark: [
          { required: true, message: "请输入标签备注", trigger: "blur" },
          { min: 3, message: "备注至少3个字符", trigger: "blur" },
        ],
      },
    };
  },
  // 渲染前执行
  created() {
    // 查询
    this.getTagList();
  },
  // 方法区
  methods: {
    // 获取标签列表
    getTagList(index = 1) {
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
      tagAPI
        .pageQueryTag(this.index, this.limit, this.queryCondition)
        .then((response) => {
          this.tagList = response.data.records;
          this.total = Number(response.data.total);
        });
    },
    // 删除文章
    deleteTag(tagId, tagName) {
      this.$confirm(`确定要删除[ ${tagName} ]吗?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        // 调用接口删除
        tagAPI.deleteTag(tagId).then((response) => {
          // 提示
          const messageBox = this.$createElement;
          this.$notify({
            message: messageBox("i", { style: "color: teal" }, "删除成功"),
            duration: 1500,
          });
          // 刷新
          this.getTagList();
        });
      });
    },
    // 每页记录数改变
    handleSizeChange(limit) {
      this.limit = limit;
      this.getTagList();
    },
    // 当前页改变
    handleCurrentChange(index) {
      this.getTagList(index);
    },
    // 重置查询条件
    resetQueryCondition() {
      this.queryCondition = {};
      this.createDateRange = [];
      this.updateDateRange = [];
      this.getTagList();
    },
    // 打开对话框
    openTagDialog(tag) {
      // 打开对话框
      this.tagDialogVisible = true;
      // 传递数据
      if (tag) {
        this.tagDialogData = { ...tag };
        this.tagDialogTitle = "编辑标签";
      } else {
        this.tagDialogData = { ...tagFormData };
        this.tagDialogTitle = "添加标签";
      }
    },
    // 更新标签
    updateTag(tag) {
      tagAPI
        .updateTag(tag)
        .then((response) => {
          this.$message.success("更新标签成功");
          // 刷新数据
          this.getTagList();
          // 关闭对话框
          this.cancelTagDialog();
        })
        .catch((error) => {
          this.$message.error("更新标签失败");
          // 关闭对话框
          this.cancelTagDialog();
        });
    },
    // 添加标签
    addTag(tag) {
      tagAPI
        .addTag(tag)
        .then((response) => {
          this.$message.success("添加标签成功");
          // 刷新数据
          this.getTagList();
          // 关闭对话框
          this.cancelTagDialog();
        })
        .catch((error) => {
          this.$message.error("添加标签失败");
          // 关闭对话框
          this.cancelTagDialog();
        });
    },
    // 添加或者更新标签
    addOrUpdateTag() {
      this.$refs.tagDialogForm.validate((valid) => {
        if (valid) {
          if (this.tagDialogData.id) {
            // 更新标签数据
            this.updateTag(this.tagDialogData);
          } else {
            // 添加标签数据
            this.addTag(this.tagDialogData);
          }
        } else {
          this.$message.warning("表单数据不合法");
        }
      });
    },
    // 关闭对话框
    cancelTagDialog() {
      this.tagDialogVisible = false;
      this.tagDialogData = { ...tagFormData };
    },
    // 启用或者禁用标签
    enableOrDisableTag(tag) {
      const { id, name, enable } = tag;
      const text = enable ? "启用" : "禁用";
      this.$confirm(`确定要${text}标签[${name}]吗?`, "系统提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          tagAPI
            .enableOrDisableTag(id, enable)
            .then((response) => {
              if (enable) {
                this.$message.success(`启用标签[${name}]成功`);
              } else {
                this.$message.warning(`禁用标签[${name}]成功`);
              }
            })
            .catch((error) => {
              tag.enable = !tag.enable;
              this.$message.error("操作失败");
            });
        })
        .catch(() => {
          tag.enable = !tag.enable;
        });
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
