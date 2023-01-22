<template>
  <div class="app-container">
    <el-input
      v-model="filterText"
      placeholder="输入关键字过滤"
      style="margin-bottom: 10px"
    />

    <el-card class="operate-container" shadow="never" align="center">
      <el-button
        size="medium"
        type="primary"
        icon="el-icon-plus"
        @click="openAddMenuDialog()"
        >添加顶层菜单</el-button
      >
    </el-card>

    <el-divider></el-divider>

    <el-table
      :data="menuList"
      style="width:100% margin-bottom:20px"
      row-key="id"
      border
      ref="menuTree"
      :filter-node-method="filterMenu"
      :tree-props="{ children: 'children' }"
    >
      <!-- 名称开始 -->
      <el-table-column prop="name" label="名称"></el-table-column>
      <!-- 名称结束 -->

      <!-- 菜单类型开始 -->
      <el-table-column prop="type" label="菜单类型" align="center">
        <template slot-scope="scope">
          <el-tag style="background: #fff0f5" v-if="scope.row.type == 0"
            >目录</el-tag
          >
          <el-tag style="background: #f0f8ff" v-if="scope.row.type == 1"
            >菜单</el-tag
          >
          <el-tag style="background: #e6e6fa" v-if="scope.row.type == 2"
            >按钮</el-tag
          >
        </template>
      </el-table-column>
      <!-- 菜单类型结束 -->

      <!-- 图标开始 -->
      <el-table-column label="菜单图标" align="center">
        <template slot-scope="scope">
          <svg-icon
            v-if="scope.row.icon != null"
            :icon-class="scope.row.icon"
          />
        </template>
      </el-table-column>
      <!-- 图标结束 -->

      <!-- 排序列开始 -->
      <el-table-column prop="sort" label="菜单排序" width="80" align="center" />
      <!-- 排序列结束 -->

      <!-- 隐藏开始 -->
      <el-table-column label="是否隐藏" width="100" align="center">
        <template slot-scope="scope">
          <el-tag type="warning" v-if="scope.row.hidden">隐藏</el-tag>
          <el-tag type="success" v-else>显示</el-tag>
        </template>
      </el-table-column>
      <!-- 隐藏结束 -->

      <!-- 是否启用开始 -->
      <el-table-column label="是否启用" width="100" align="center">
        <template slot-scope="scope">
          <el-switch
            style="display: block"
            v-model="scope.row.enable"
            active-color="#13ce66"
            inactive-color="#ff4949"
            @change="enableOrDisableMenu(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <!-- 是否启用结束 -->

      <!-- 权限值开始 -->
      <el-table-column prop="permissionValue" label="权限值"></el-table-column>
      <!-- 权限值结束 -->

      <!-- 访问路径开始 -->
      <el-table-column prop="path" label="访问路径"></el-table-column>
      <!-- 访问路径结束 -->

      <!-- 组件路径开始 -->
      <el-table-column prop="component" label="组件路径"></el-table-column>
      <!-- 组件路径结束 -->

      <!-- 操作列开始 -->
      <el-table-column label="操作" width="150" align="right">
        <template slot-scope="scope">
          <!-- 如果是顶级或者一级菜单,则可以继续添加菜单 -->
          <el-button
            type="text"
            size="mini"
            @click="openAddMenuDialog(scope.row.id)"
            >添加菜单
          </el-button>
          <!-- 所有菜单均可修改 -->
          <el-button
            type="text"
            size="mini"
            @click="openUpdateMenuDialog(scope.row)"
            >修改
          </el-button>
          <!-- 所有菜单均可删除 -->
          <el-button type="text" size="mini" @click="deleteMenu(scope.row)"
            >删除
          </el-button>
        </template>
      </el-table-column>
      <!-- 操作列结束 -->
    </el-table>

    <!-- 添加或修改菜单的窗口 -->
    <el-dialog
      :visible.sync="menuDialogVisible"
      :title="menuDialogTitle"
      width="680px"
      append-to-body
    >
      <el-form
        ref="menuDialogForm"
        :model="menuDialogData"
        :rules="menuDialogRules"
        label-width="100px"
      >
        <el-row>
          <!-- 上级菜单 -->
          <el-col :span="24">
            <el-form-item label="上级菜单">
              <treeselect
                v-model="menuDialogData.pid"
                :options="menuOptions"
                :normalizer="normalizerMenu"
                :show-count="true"
                :clearable="false"
                placeholder="选择上级菜单"
              />
            </el-form-item>
          </el-col>
          <!-- 菜单类型 -->
          <el-col :span="24">
            <el-form-item label="菜单类型" prop="type">
              <el-radio-group v-model="menuDialogData.type">
                <el-radio :label="0">目录</el-radio>
                <el-radio :label="1">菜单</el-radio>
                <el-radio :label="2">按钮</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <!-- 菜单图标 -->
          <el-col :span="24" v-if="menuDialogData.type != 2">
            <el-form-item label="菜单图标">
              <el-select
                v-model="menuDialogData.icon"
                placeholder="图标"
                clearable
                filterable
                size="small"
                style="width: 240px"
              >
                <el-option
                  v-for="(item, index) in iconList"
                  :key="index"
                  :value="item"
                >
                  <svg-icon :icon-class="item" />
                  <span>-{{ item }}</span>
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <!-- 菜单名称 -->
          <el-col :span="12">
            <el-form-item label="菜单名称" prop="name">
              <el-input
                v-model="menuDialogData.name"
                placeholder="请输入菜单名称"
              />
            </el-form-item>
          </el-col>
          <!-- 菜单排序 -->
          <el-col :span="12">
            <el-form-item label="显示排序" prop="sort">
              <el-input-number
                v-model="menuDialogData.sort"
                controls-position="right"
                :min="0"
              />
            </el-form-item>
          </el-col>
          <!-- 路由地址 -->
          <el-col v-if="menuDialogData.type != 2" :span="24">
            <el-form-item prop="path">
              <span slot="label">
                <el-tooltip
                  content="访问的路由地址，如：`user`"
                  placement="top"
                >
                  <i class="el-icon-question" />
                </el-tooltip>
                路由地址
              </span>
              <el-input
                v-model="menuDialogData.path"
                placeholder="请输入路由地址"
              />
            </el-form-item>
          </el-col>
          <!-- 重定向地址 -->
          <el-col v-if="menuDialogData.type == 0" :span="24">
            <el-form-item prop="redirect">
              <span slot="label">
                <el-tooltip
                  content="重定向地址，如：`/permission/user`"
                  placement="top"
                >
                  <i class="el-icon-question" />
                </el-tooltip>
                重定向地址
              </span>
              <el-input
                v-model="menuDialogData.redirect"
                placeholder="请输入重定向地址"
              />
            </el-form-item>
          </el-col>
          <!-- 组件路径 -->
          <el-col v-if="menuDialogData.type == 1" :span="12">
            <el-form-item prop="component">
              <span slot="label">
                <el-tooltip
                  content="访问的组件路径，如：`system/user/index`，默认在`views`目录下"
                  placement="top"
                >
                  <i class="el-icon-question" />
                </el-tooltip>
                组件路径
              </span>
              <el-input
                v-model="menuDialogData.component"
                placeholder="请输入组件路径"
              />
            </el-form-item>
          </el-col>
          <!-- 权限字符 -->
          <el-col v-if="menuDialogData.type != 0" :span="12">
            <el-form-item>
              <el-input
                v-model="menuDialogData.permissionValue"
                placeholder="请输入权限标识"
                maxlength="100"
              />
              <span slot="label">
                <el-tooltip
                  content="控制器中定义的权限字符，如：@PreAuthorize(`@ss.hasPermi('system:user:list')`)"
                  placement="top"
                >
                  <i class="el-icon-question" />
                </el-tooltip>
                权限字符
              </span>
            </el-form-item>
          </el-col>
          <!-- 显示状态和菜单状态(hidden,enable) -->
          <el-col v-if="menuDialogData.type != 2" :span="12">
            <el-form-item>
              <span slot="label">
                <el-tooltip
                  content="选择隐藏则路由将不会出现在侧边栏，但仍然可以访问"
                  placement="top"
                >
                  <i class="el-icon-question" />
                </el-tooltip>
                显示状态
              </span>
              <el-radio-group v-model="menuDialogData.hidden">
                <el-radio :key="false" :label="false">显示</el-radio>
                <el-radio :key="true" :label="true">隐藏</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col v-if="menuDialogData.type != 2" :span="12">
            <el-form-item>
              <span slot="label">
                <el-tooltip
                  content="选择停用则路由将不会出现在侧边栏，也不能被访问"
                  placement="top"
                >
                  <i class="el-icon-question" />
                </el-tooltip>
                菜单状态
              </span>
              <el-radio-group v-model="menuDialogData.enable">
                <el-radio :key="true" :label="true">启用</el-radio>
                <el-radio :key="false" :label="false">停用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!-- 按钮区 -->
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addOrUpdateMenu()">确 定</el-button>
        <el-button @click="cancelMenuDialog()">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import menuAPI from "@/api/menu";
// import the component
import Treeselect from "@riophae/vue-treeselect";
// import the styles
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

// 表单数据
const menuForm = {
  // 菜单id
  id: undefined,
  // 菜单名
  name: "",
  // 菜单父id
  pid: 0,
  // 菜单访问路径
  path: "",
  // 菜单组件路径
  component: "",
  // 重定向路径
  redirect: "",
  // 菜单类型(0为目录,1为菜单,2为按钮)
  type: 0,
  // 图标
  icon: "",
  // 是否隐藏
  hidden: false,
  // 排序
  sort: 0,
  // 是否启用
  enable: true,
};

export default {
  name: "Menu",
  components: {
    Treeselect,
  },
  data() {
    return {
      filterText: "",
      // 菜单列表
      menuList: [],
      // 添加菜单的对话框标题
      menuDialogTitle: "添加菜单",
      // 是否显示菜单对话框
      menuDialogVisible: false,
      // 要提交的菜单和按钮权限实体
      menuDialogData: { ...menuForm },
      // 菜单输入规则
      menuDialogRules: {
        name: [{ required: true, message: "请输入菜单名", trigger: "blur" }],
        path: [{ required: true, message: "请输入菜单路径", trigger: "blur" }],
        component: [
          { required: true, message: "请输入组件名称", trigger: "blur" },
        ],
        permissionValue: [
          { required: true, message: "请输入权限字符", trigger: "blur" },
        ],
      },
      // 图标列表
      iconList: [],

      // treeSelect组件数据
      menuOptions: [],
    };
  },
  // 监听上面文本框搜索
  watch: {
    // 过滤权限
    filterText(val) {
      this.$refs.menuTree.filter(val);
    },
  },
  // 创建前执行
  created() {
    this.getAllMenu();
    this.iconList = this.getIconList();
  },
  methods: {
    // 获取所有的权限菜单
    getAllMenu() {
      menuAPI.getAllMenu().then((response) => {
        this.menuList = response.data;
      });
    },
    // 打开增加对话框
    openAddMenuDialog(pid) {
      // 显示对话框
      this.menuDialogVisible = true;
      this.menuDialogTitle = "添加菜单";
      // 更新menu数据
      this.menuDialogData = { ...menuForm };
      if (pid) {
        this.menuDialogData.pid = pid;
      }
      // 获取treeSelect数据
      this.getTreeSelectData();
    },
    // 打开修改对话框
    openUpdateMenuDialog(menu) {
      // 显示对话框
      this.menuDialogVisible = true;
      this.menuDialogTitle = "修改菜单";
      // 更新menu数据
      this.menuDialogData = { ...menu };
      // 获取treeSelect数据
      this.getTreeSelectData();
    },
    // 添加菜单
    addMenu(menu) {
      menuAPI
        .addMenu(menu)
        .then((response) => {
          // 关闭对话框
          this.cancelMenuDialog();
          // 提示信息
          this.$message.success("添加菜单成功");
          // 获取新数据
          this.getAllMenu();
        })
        .catch((error) => {
          // 关闭对话框
          this.cancelMenuDialog();
          // 提示信息
          this.$message.error("添加菜单失败:" + error);
        });
    },
    // 更新菜单
    updateMenu(menu) {
      menuAPI.updateMenu(menu).then((response) => {
        // 关闭对话框
        this.cancelMenuDialog();
        // 提示信息
        this.$message.success("修改菜单成功");
        // 获取新数据
        this.getAllMenu();
      });
    },
    // 保存或者更新菜单
    addOrUpdateMenu() {
      this.$refs.menuDialogForm.validate((valid) => {
        if (valid) {
          if (this.menuDialogData.id) {
            // 修改菜单信息
            this.updateMenu(this.menuDialogData);
          } else {
            // 添加菜单信息
            this.addMenu(this.menuDialogData);
          }
        } else {
          this.$message.warning("表单数据不合法");
        }
      });
    },
    // 关闭对话框
    cancelMenuDialog() {
      this.menuDialogVisible = false;
      this.menu = { ...menuForm };
    },
    // 删除菜单
    deleteMenu(menu) {
      this.$confirm(`确定要删除[ ${menu.name} ]吗?`, "系统提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        // 调用接口删除
        menuAPI.deleteMenu(menu.id).then((response) => {
          // 提示信息
          this.$notify.success({
            message: "删除菜单成功",
            duration: 1500,
          });
          // 刷新
          this.getAllMenu();
        });
      });
    },
    // 启用或者禁用菜单
    enableOrDisableMenu(menu) {
      const { id, name, enable } = menu;
      const text = enable ? "启用" : "禁用";
      this.$confirm(`确定要${text}菜单[${name}]吗?`, "系统提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          menuAPI
            .enableOrDisableMenu(id, enable)
            .then((response) => {
              if (enable) {
                this.$message.success(`启用菜单[${name}]成功`);
              } else {
                this.$message.warning(`禁用菜单[${name}]成功`);
              }
            })
            .catch((error) => {
              menu.enable = !menu.enable;
              this.$message.error("操作失败");
            });
        })
        .catch(() => {
          menu.enable = !menu.enable;
        });
    },
    // 获取图标列表
    getIconList() {
      const req = require.context("@/icons/svg", false, /\.svg$/);
      const requireAll = (requireContext) => requireContext.keys();
      const re = /\.\/(.*)\.svg/;

      const arr = requireAll(req).map((i) => {
        return i.match(re)[1];
      });
      return arr;
    },
    // treeSelect组件函数
    // 转换菜单数据结构
    normalizerMenu(node) {
      if (node.children && !node.children.length) {
        // 如果children为空
        delete node.children;
      }
      return {
        id: node.id,
        label: node.name,
        children: node.children,
      };
    },
    // 获取菜单数据
    getTreeSelectData() {
      menuAPI.getTreeSelectMenu().then((response) => {
        this.menuOptions = [];
        const parent = { id: 0, name: "主类目", children: [] };
        parent.children = response.data;
        this.menuOptions.push(parent);
      });
    },
    // 过滤菜单
    filterMenu(value, data) {
      if (!value) return true;
      return data.name.indexOf(value) !== -1;
    },
  },
};
</script>
