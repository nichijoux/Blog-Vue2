<template>
  <div class="app-container">
    <!-- 查询表单开始 -->
    <el-card class="operate-container" shadow="never" align="center">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="用户账号">
          <el-input v-model="queryCondition.account" placeholder="用户账号" />
        </el-form-item>

        <el-form-item label="用户类型">
          <el-select
            v-model="queryCondition.type"
            clearable
            placeholder="管理员"
          >
            <el-option :value="true" label="管理员" />
            <el-option :value="false" label="普通用户" />
          </el-select>
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

        <!-- 查询表单操作开始 -->
        <div>
          <el-button type="primary" icon="el-icon-search" @click="getUserList()"
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
        class="user-button"
        @click="openUserInfoDialog()"
        >添加用户</el-button
      >
      <el-button
        type="danger"
        v-hasPermission="'permission::user::manage::batchDelete'"
        class="user-button"
        @click="batchDeleteUser()"
        >批量删除</el-button
      >
    </div>

    <el-divider></el-divider>

    <!-- 用户列表数据开始 -->
    <el-table
      v-loading="dataLoading"
      :data="userList"
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

      <el-table-column prop="account" label="账号" align="center" />

      <el-table-column prop="nickname" label="昵称" align="center" />

      <el-table-column label="用户头像" width="150" align="center">
        <template slot-scope="scope">
          <img
            :src="scope.row.avatar"
            width="130"
            height="130"
            class="el-image__inner el-image__preview"
          />
        </template>
      </el-table-column>

      <el-table-column prop="email" label="邮箱" align="center" />

      <el-table-column prop="phone" label="电话号码" align="center" />

      <!-- 用户类型 -->
      <el-table-column label="用户类型" align="center">
        <template slot-scope="scope">
          <el-tag size="medium" v-if="scope.row.type" type="error"
            >管理员</el-tag
          >
          <el-tag size="medium" v-else type="success">普通用户</el-tag>
        </template>
      </el-table-column>

      <!-- 是否启用开始 -->
      <el-table-column label="是否启用" align="center">
        <template slot-scope="scope">
          <el-switch
            style="display: block"
            v-model="scope.row.enable"
            active-color="#13ce66"
            inactive-color="#ff4949"
            @change="enableOrDisableUser(scope.row)"
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
            v-hasPermission="'permission::user::manage::assign_role'"
            @click="() => openUserRoleDialog(scope.row)"
            >分配角色</el-button
          >
          <!-- 编辑角色按钮 -->
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-edit"
            v-hasPermission="'permission::user::manage::update'"
            @click="() => openUserInfoDialog(scope.row)"
            >编辑用户</el-button
          >
          <!-- 删除角色按钮 -->
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            v-hasPermission="'permission::user::manage::delete'"
            @click="deleteUser(scope.row.id, scope.row.account)"
            >删除用户</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 用户列表数据结束 -->

    <!-- 用户信息修改对话框开始 -->
    <el-dialog
      :visible.sync="userInfoDialogVisible"
      :title="userInfoDialogTitle"
      append-to-body
      ><el-form
        :model="userInfo"
        :rules="userInfoRules"
        ref="userInfoForm"
        label-width="120px"
      >
        <el-row>
          <!-- 用户账号 -->
          <el-col :span="12">
            <el-form-item v-if="userInfo.id == undefined" prop="account">
              <span slot="label">
                <el-tooltip content="用户登录时需要输入的账号" placement="top">
                  <i class="el-icon-question" />
                </el-tooltip>
                用户账号
              </span>
              <el-input
                v-model="userInfo.account"
                placeholder="请输入用户账号"
                maxlength="30"
              />
            </el-form-item>
          </el-col>
          <!-- 用户密码 -->
          <el-col :span="12">
            <el-form-item
              v-if="userInfo.id == undefined"
              label="用户密码"
              prop="password"
            >
              <el-input
                v-model="userInfo.password"
                placeholder="请输入用户密码"
                type="password"
                maxlength="20"
                show-password
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item prop="nickname">
              <span slot="label">
                <el-tooltip content="用户将要显示的昵称" placement="top">
                  <i class="el-icon-question" />
                </el-tooltip>
                用户昵称
              </span>
              <el-input
                v-model="userInfo.nickname"
                placeholder="请输入用户昵称"
                maxlength="30"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="手机号码" prop="phone">
              <el-input
                v-model="userInfo.phone"
                placeholder="请输入手机号码"
                maxlength="11"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input
                v-model="userInfo.email"
                placeholder="请输入邮箱"
                maxlength="50"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :span="24">
          <el-form-item label="状态">
            <el-radio-group v-model="userInfo.enable">
              <el-radio :key="true" :label="true">启用</el-radio>
              <el-radio :key="false" :label="false">停用</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-row>
      </el-form>
      <!-- 保存和取消按钮 -->
      <div slot="footer" class="dialog-footer m-center">
        <el-button type="primary" @click="addOrUpdateUserInfo()"
          >确定</el-button
        >
        <el-button type="warning" @click="closeUserInfoDialog()"
          >取消</el-button
        >
      </div>
    </el-dialog>
    <!-- 用户信息修改对话框结束 -->

    <!-- 用户角色分配对话框开始 -->
    <el-dialog
      :visible.sync="userRoleDialogVisible"
      title="角色分配"
      append-to-body
    >
      <div class="m-center">
        <el-transfer
          style="text-align: left; display: inline-block"
          key="id"
          filterable
          v-model="userRoleSelectList"
          :titles="['未分配', '已分配']"
          :data="roleList"
          :props="{
            key: 'id',
            label: 'name',
          }"
        >
        </el-transfer>
      </div>
      <div slot="footer" class="dialog-footer m-center">
        <el-button type="primary" @click="assignUserRole()">确定</el-button>
        <el-button type="warning" @click="closeRoleAssignDialog()"
          >取消</el-button
        >
      </div>
    </el-dialog>
    <!-- 用户角色分配对话框结束 -->

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
import userAPI from "@/api/user";
import roleAPI from "@/api/role";
import md5 from "js-md5";

// 用户信息表单
const userInfoForm = {
  // 账号
  account: "",
  // 密码
  password: "",
  // 昵称
  nickname: "",
  // 手机
  phone: "",
  // email
  email: "",
  // 是否启用
  enable: true,
};

export default {
  // 数据区
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
      // 用户列表
      userList: [],
      // 查询条件
      queryCondition: {},
      // 批量选择中选择的记录列表
      multipleSelection: [],

      // 用户信息修改部分
      // 编辑用户信息的对象
      userInfo: { ...userInfoForm },
      // 编辑用户信息对话框标题和显示
      userInfoDialogVisible: false,
      userInfoDialogTitle: "",
      // 用户信息表单规则
      userInfoRules: {
        account: [
          { required: true, message: "请输入用户账号", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 6, message: "密码至少为6个字符", trigger: "blur" },
        ],
        nickname: [
          { required: true, message: "请输入用户昵称", trigger: "blur" },
        ],
        phone: [{ required: true, message: "请输入手机号", trigger: "blur" }],
        email: [{ required: true, message: "请输入邮箱", trigger: "blur" }],
      },
      // 用户角色分配部分
      // 用户角色对话框显示
      userRoleDialogVisible: false,
      // 用户角色
      userId: undefined,
      // 用户选择的角色
      userRoleSelectList: [],
      // 所有角色列表
      roleList: [],
    };
  },
  // 渲染前执行
  created() {
    this.getUserList();
  },
  // 方法区
  methods: {
    // 获取用户列表
    getUserList(index = 1) {
      this.index = index;
      // API查询
      userAPI
        .pageQueryUser(this.index, this.limit, this.queryCondition)
        .then((response) => {
          this.userList = response.data.records;
          this.total = Number(response.data.total);
          // 数据加载并绑定成功
          this.dataLoading = false;
        });
    },
    // 删除用户
    deleteUser(userId, account) {
      this.$confirm(`确定要删除用户[ ${account} ]吗?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        userAPI.deleteUser(userId).then((response) => {
          this.$message.success("删除成功");
          this.getUserList();
        });
      });
    },
    // 批量删除用户
    batchDeleteUser() {
      if (this.multipleSelection.length === 0) {
        this.$message.warning("请选择要删除的记录");
        return;
      }
      this.$confirm("此操作将永久删除这些用户, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          // 遍历selecttion将id去除放入id列表
          let idList = [];
          this.multipleSelection.forEach((item) => {
            idList.push(item.id);
          });
          // 调用API
          userAPI.batchDeleteUser(idList).then((response) => {
            this.$message.success("删除成功");
            // 刷新用户数据
            this.getUserList();
          });
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },
    // 启用或者禁用用户
    enableOrDisableUser(user) {
      const text = user.enable ? "启用" : "禁用";
      const account = user.account;
      this.$confirm(`确定要${text}用户${account}吗?`, "系统提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          userAPI
            .enableOrDisableUser(user.id, user.enable)
            .then((response) => {
              if (user.enable) {
                this.$message.success(`启用用户[${account}]成功`);
              } else {
                this.$message.warning(`禁用用户[${account}]成功`);
              }
            })
            .catch(() => {
              user.enable = !user.enable;
              this.$message.error("操作失败");
            });
        })
        .catch(() => {
          user.enable = !user.enable;
        });
    },
    // 添加用户信息
    addUserInfo(userInfo) {
      userAPI
        .addUser(userInfo)
        .then((response) => {
          this.$message.success("添加用户成功");
          // 刷新数据
          this.getUserList();
          // 关闭对话框
          this.userInfoDialogVisible = false;
          // 重置表单信息
          this.userInfo = { ...userInfoForm };
        })
        .catch((err) => {
          this.$message.error("添加角色信息失败" + err);
          // 关闭对话框
          this.userInfoDialogVisible = false;
          // 重置表单信息
          this.userInfo = { ...userInfoForm };
        });
    },
    // 修改用户信息
    updateUserInfo(userInfo) {
      userAPI
        .updateUser(userInfo)
        .then((response) => {
          this.$message.success("修改用户成功");
          // 刷新数据
          this.getUserList();
          // 关闭对话框
          this.userInfoDialogVisible = false;
          // 重置表单信息
          this.userInfo = { ...userInfoForm };
        })
        .catch((err) => {
          this.$message.error("修改角色信息失败" + err);
          // 关闭对话框
          this.userInfoDialogVisible = false;
          // 重置表单信息
          this.userInfo = { ...userInfoForm };
        });
    },
    // 添加或者修改用户信息
    addOrUpdateUserInfo() {
      this.$refs.userInfoForm.validate((valid) => {
        if (valid) {
          const data = { ...this.userInfo };
          if (data.id) {
            // 更新用户数据
            this.updateUserInfo(data);
          } else {
            // 添加用户数据
            // 密码MD5加密
            data.password = md5(data.password);
            this.addUserInfo(data);
          }
        } else {
          this.$message.warning("表单数据不合法");
        }
      });
    },
    // 打开用户信息对话框
    openUserInfoDialog(user) {
      // 打开对话框
      this.userInfoDialogVisible = true;
      // 传递数据
      if (user) {
        this.userInfo = { ...user };
        this.userInfoDialogTitle = "编辑用户信息";
      } else {
        this.userInfo = { ...userInfoForm };
        this.userInfoDialogTitle = "添加用户";
      }
    },
    // 关闭用户信息对话框
    closeUserInfoDialog() {
      this.userInfoDialogVisible = false;
      this.userInfo = { ...userInfoForm };
    },

    // 打开用户角色分配对话框
    openUserRoleDialog(user) {
      this.userRoleDialogVisible = true;
      this.userId = user.id;
      // 获取选中的角色
      roleAPI.getUserRole(user.id).then((res) => {
        this.roleList = res.data.roleList;
        this.userRoleSelectList = res.data.assignRoleIdList;
      });
    },
    // 更新用户角色
    assignUserRole() {
      const roleIdList = [];
      this.userRoleSelectList.forEach((roleId) => {
        roleIdList.push(roleId);
      });
      roleAPI
        .assignUserRole(this.userId, roleIdList.toString())
        .then((response) => {
          this.$message.success("保存成功");
          this.userRoleDialogVisible = false;
        });
    },
    // 关闭角色分配对话框
    closeRoleAssignDialog() {
      // 关闭对话框
      this.userRoleDialogVisible = false;
      // 重置数据
      this.userId = undefined;
      this.roleList = [];
      this.userRoleSelectList = [];
    },

    // 当表格复选框选项发生变化的时候触发
    handleSelectionChange(selection) {
      this.multipleSelection = selection;
    },
    // 每页记录数改变
    handleSizeChange(limit) {
      this.limit = limit;
      this.getUserList();
    },
    // 当前页改变
    handleCurrentChange(index) {
      this.getUserList(index);
    },
    // 重置查询条件
    resetQueryCondition() {
      this.queryCondition = {};
      this.getUserList();
    },
  },
};
</script>

<style scoped>
.user-button {
  width: 100px;
}
.m-center {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
