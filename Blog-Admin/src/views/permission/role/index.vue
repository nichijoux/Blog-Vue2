<template>
  <div class="app-container">
    <!-- 查询表单开始 -->
    <el-card class="operate-container" shadow="never" align="center">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="角色名">
          <el-input v-model="queryCondition.name" placeholder="角色名" />
        </el-form-item>

        <el-form-item label="角色权限字符串">
          <el-input v-model="queryCondition.key" placeholder="权限字符串" />
        </el-form-item>

        <el-form-item label="是否启用">
          <el-select
            v-model="queryCondition.enable"
            clearable
            placeholder="启用"
          >
            <el-option :value="true" label="已启用" />
            <el-option :value="false" label="未启用" />
          </el-select>
        </el-form-item>

        <!-- 查询表单操作开始 -->
        <div>
          <el-button type="primary" icon="el-icon-search" @click="getRoleList()"
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

    <!-- 工具条 -->
    <div class="m-center">
      <el-button
        type="success"
        class="role-button"
        @click="openRoleInfoDialog()"
        >添加角色</el-button
      >
      <el-button type="danger" class="role-button" @click="batchDeleteRole()"
        >批量删除</el-button
      >
    </div>

    <el-divider></el-divider>

    <!-- 角色列表 -->
    <el-table
      v-loading="dataLoading"
      :data="roleList"
      stripe
      border
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />

      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (index - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="name" label="角色名称" align="center" />

      <el-table-column prop="key" label="角色权限字符串" align="center" />

      <el-table-column prop="remark" label="角色备注" align="center" />

      <el-table-column prop="sort" label="角色排序" align="center" />

      <!-- 是否启用开始 -->
      <el-table-column label="是否启用" align="center">
        <template slot-scope="scope">
          <el-switch
            style="display: block"
            v-model="scope.row.enable"
            active-color="#13ce66"
            inactive-color="#ff4949"
            @change="enableOrDisableRole(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <!-- 是否启用结束 -->

      <el-table-column label="操作" width="360px" align="center">
        <template slot-scope="scope">
          <el-button
            type="warning"
            size="mini"
            icon="el-icon-setting"
            @click="() => openRolePermissionDialog(scope.row)"
            >分配权限</el-button
          >
          <!-- 编辑角色按钮 -->
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-edit"
            @click="() => openRoleInfoDialog(scope.row)"
            >编辑角色</el-button
          >
          <!-- 删除角色按钮 -->
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="deleteRole(scope.row)"
            >删除角色</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 编辑角色信息对话框开始 -->
    <el-dialog
      :visible.sync="roleInfoDialogVisible"
      :title="roleInfoDialogTitle"
      width="500px"
      append-to-body
    >
      <el-form
        ref="roleInfoForm"
        :model="roleInfo"
        :rules="roleInfoRules"
        label-width="100px"
      >
        <el-form-item prop="name">
          <span slot="label">
            <el-tooltip
              content="给用户分配角色时显示的角色名称"
              placement="top"
            >
              <i class="el-icon-question" />
            </el-tooltip>
            角色名称
          </span>
          <el-input v-model="roleInfo.name" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item prop="key">
          <span slot="label">
            <el-tooltip
              content="controller中定义的权限字符，如：@PreAuthorize(`@ps.hasRole('admin')`)"
              placement="top"
            >
              <i class="el-icon-question" />
            </el-tooltip>
            权限字符
          </span>
          <el-input v-model="roleInfo.key" placeholder="请输入权限字符" />
        </el-form-item>
        <el-form-item label="角色顺序" prop="sort">
          <el-input-number
            v-model="roleInfo.sort"
            controls-position="right"
            :min="0"
          />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="roleInfo.enable">
            <el-radio :key="true" :label="true">启用</el-radio>
            <el-radio :key="false" :label="false">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注">
          <el-input
            v-model="roleInfo.remark"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer m-center">
        <el-button type="primary" @click="addOrUpdateRoleInfo()"
          >确定</el-button
        >
        <el-button type="warning" @click="resetRoleInfo()">取消</el-button>
      </div>
    </el-dialog>
    <!-- 编辑角色信息对话框结束 -->

    <!-- 编辑角色权限对话框开始 -->
    <el-dialog :visible.sync="rolePermissionDialogVisible" title="分配角色权限">
      <el-tree
        :data="rolePermission"
        show-checkbox
        node-key="id"
        ref="permissionTree"
        highlight-current
        :props="permissionTreeProps"
      ></el-tree>
      <div class="m-center">
        <el-button type="primary" @click="updateRolePermission()"
          >保存</el-button
        >
        <el-button type="warning" @click="resetRolePermission()"
          >取消</el-button
        >
      </div>
    </el-dialog>
    <!-- 编辑角色权限对话框结束 -->

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
import roleAPI from "@/api/role";
import menuAPI from "@/api/menu";

// 编辑角色的对象
const roleInfoForm = {
  // 角色名
  name: "",
  // 角色权限字符串
  key: "",
  // 角色备注
  remark: "",
  // 角色是否启用
  enable: true,
  // 角色排序
  sort: 0,
};

// 编辑权限的对象
const rolePermissionTree = [];

export default {
  data() {
    return {
      // 数据是否正在加载
      dataLoading: true,
      // 当前页
      index: 1,
      // 每页记录数
      limit: 10,
      // 总记录条数
      total: 0,
      // 角色列表
      roleList: [],
      // 查询条件
      queryCondition: {},
      // 批量选择中选择的记录列表
      multipleSelection: [],

      // 角色信息部分
      // 编辑角色的对象
      roleInfo: { ...roleInfoForm },
      // 编辑角色对话框标题和显示
      roleInfoDialogTitle: "编辑角色信息",
      roleInfoDialogVisible: false,
      // 角色信息表单规则
      roleInfoRules: {
        name: [{ required: true, message: "请输入角色名", trigger: "blur" }],
        key: [
          { required: true, message: "请输入角色权限字符串", trigger: "blur" },
        ],
        remark: [
          { required: true, message: "请输入角色备注", trigger: "blur" },
        ],
      },
      // 角色权限部分
      // 要编辑的角色id
      roleId: "",
      // 编辑角色权限的对象
      rolePermission: [],
      // 编辑角色对话框
      rolePermissionDialogVisible: false,
      permissionTreeProps: {
        children: "children",
        label: "name",
      },
    };
  },

  // 生命周期函数：内存准备完毕，页面尚未渲染
  created() {
    // 获取角色信息
    this.getRoleList();
  },

  methods: {
    // 加载角色列表数据
    getRoleList(index = 1) {
      this.index = index;
      // API查询
      roleAPI
        .pageQueryRole(this.index, this.limit, this.queryCondition)
        .then((response) => {
          this.roleList = response.data.records;
          this.total = Number(response.data.total);
          // 数据加载并绑定成功
          this.dataLoading = false;
        });
    },
    // 根据id删除数据
    deleteRole(role) {
      const { id, name } = role;
      this.$confirm(`确定要删除角色[ ${name} ]吗?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          roleAPI.deleteRole(id).then((response) => {
            this.$message.success("删除成功");
            this.getRoleList();
          });
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },
    // 批量删除
    batchDeleteRole() {
      if (this.multipleSelection.length === 0) {
        this.$message.warning("请选择要删除的记录!");
        return;
      }
      this.$confirm("此操作将永久删除这些角色, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          // 遍历selection，将id取出放入id列表
          let idList = [];
          this.multipleSelection.forEach((item) => {
            idList.push(item.id);
          });
          // 调用api
          roleAPI.batchDeleteRole(idList).then((response) => {
            this.$message.success("删除成功");
            this.getRoleList();
          });
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },
    // 当表格复选框选项发生变化的时候触发
    handleSelectionChange(selection) {
      this.multipleSelection = selection;
    },
    // 每页记录数改变
    handleSizeChange(limit) {
      this.limit = limit;
      this.getRoleList();
    },
    // 当前页改变
    handleCurrentChange(index) {
      this.getRoleList(index);
    },
    // 重置查询条件
    resetQueryCondition() {
      this.queryCondition = {};
      this.getRoleList();
    },
    // 编辑角色信息对话框
    openRoleInfoDialog(data) {
      // 打开对话框
      this.roleInfoDialogVisible = true;
      // 赋予表单对象
      if (data) {
        this.roleInfo = { ...data };
        this.roleInfoDialogTitle = "编辑角色信息";
      } else {
        this.roleInfo = { ...roleInfoForm };
        this.roleInfoDialogTitle = "添加角色";
      }
    },
    // 添加角色
    addRoleInfo(roleInfo) {
      roleAPI
        .addRole(roleInfo)
        .then((response) => {
          this.$message.success("添加角色成功");
          // 获取数据
          this.getRoleList();
          // 关闭对话框
          this.roleInfoDialogVisible = false;
          // 重置表单
          this.roleInfo = { ...roleInfoForm };
        })
        .catch((error) => {
          // 关闭对话框
          this.roleInfoDialogVisible = false;
          // 重置表单
          this.roleInfo = { ...roleInfoForm };
          this.$message.error("添加角色失败" + error);
        });
    },
    // 修改角色信息
    updateRoleInfo(roleInfo) {
      roleAPI
        .updateRole(roleInfo)
        .then((response) => {
          this.$message.success("更新成功");
          // 获取数据
          this.getRoleList();
          // 关闭对话框
          this.roleInfoDialogVisible = false;
          // 重置表单
          this.roleInfo = { ...roleInfoForm };
        })
        .catch((error) => {
          // 关闭对话框
          this.roleInfoDialogVisible = false;
          // 重置表单
          this.roleInfo = { ...roleInfoForm };
          this.$message.error("修改角色信息失败" + error);
        });
    },
    // 添加或者更新角色信息
    addOrUpdateRoleInfo() {
      this.$refs.roleInfoForm.validate((valid) => {
        if (valid) {
          if (this.roleInfo.id) {
            this.updateRoleInfo(this.roleInfo);
          } else {
            this.addRoleInfo(this.roleInfo);
          }
        } else {
          this.$message.warning("表单不合法");
        }
      });
    },
    // 启用或禁用角色
    enableOrDisableRole(role) {
      const { id, name, enable } = role;
      const text = enable ? "启用" : "禁用";
      this.$confirm(`确定要${text}角色[${name}]吗?`, "系统提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          roleAPI
            .enableOrDisableRole(id, enable)
            .then((response) => {
              if (enable) {
                this.$message.success(`启用角色[${name}]成功`);
              } else {
                this.$message.warning(`禁用角色[${name}]成功`);
              }
            })
            .catch((error) => {
              role.enable = !role.enable;
              this.$message.error("操作失败");
            });
        })
        .catch(() => {
          role.enable = !role.enable;
        });
    },
    // 重置RoleInfo表单
    resetRoleInfo() {
      // 关闭对话框
      this.roleInfoDialogVisible = false;
      // 重置表单
      this.roleInfo = { ...roleInfoForm };
    },
    // 获取角色对应的权限
    getRolePermission(roleId) {
      menuAPI.getAssignedMenu(roleId).then((response) => {
        this.rolePermission = response.data.menuTree;
        // 设置被选择的格子
        response.data.selectMenuIdList.forEach((value) => {
          this.$nextTick(() => {
            this.$refs.permissionTree.setChecked(value, true, false);
          });
        });
      });
    },
    // 打开权限分配框
    openRolePermissionDialog(role) {
      this.rolePermissionDialogVisible = true;
      this.roleId = role.id;
      this.getRolePermission(role.id);
    },
    // 更新角色的权限
    updateRolePermission() {
      // 获取选中的id列表
      let idList = this.$refs.permissionTree
        .getHalfCheckedKeys()
        .concat(this.$refs.permissionTree.getCheckedKeys())
        .join(",");
      // 调用API分配权限
      menuAPI.assignRoleMenu(this.roleId, idList).then((response) => {
        this.$message.success("修改权限成功");
        this.rolePermissionDialogVisible = false;
      });
    },
    // 关闭角色权限菜单
    resetRolePermission() {
      this.rolePermissionDialogVisible = false;
      this.rolePermission = [...rolePermissionTree];
    },
  },
};
</script>

<style scoped>
.role-button {
  width: 100px;
}
.m-center {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
