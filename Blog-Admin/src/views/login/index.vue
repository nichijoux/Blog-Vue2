<template>
  <div class="login-container">
    <el-form
      ref="loginForm"
      :model="loginForm"
      :rules="loginRules"
      class="login-form"
      auto-complete="on"
      label-position="left"
    >
      <!-- 标题开始 -->
      <div class="title-container">
        <h3 class="title">博客后台管理系统</h3>
      </div>
      <!-- 标题结束 -->

      <!-- 用户名开始 -->
      <el-form-item prop="account">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          ref="account"
          v-model="loginForm.account"
          placeholder="用户账号"
          name="account"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>
      <!-- 用户名结束 -->

      <!-- 密码开始 -->
      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="用户密码"
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        />
        <span class="show-pwd" @click="showPassword">
          <svg-icon
            :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"
          />
        </span>
      </el-form-item>
      <!-- 密码结束 -->

      <!-- 登录按钮开始 -->
      <el-button
        :loading="loading"
        type="primary"
        style="width: 100%; margin-bottom: 30px"
        @click.native.prevent="handleLogin"
        >登录</el-button
      >
      <!-- 登录按钮结束 -->
    </el-form>

    <!-- 滑动对话框 -->
    <el-dialog
      :visible.sync="verifyDialog"
      title="滑动验证框"
      align="center"
      append-to-body
      custom-class="m-dialog"
    >
      <slide-verify
        :l="42"
        :r="10"
        :w="310"
        :h="155"
        :imgs="verifyImages"
        @success="onSuccess"
        @fail="onFailure"
        @refresh="onRefresh"
        :slider-text="verifyText"
      ></slide-verify>
      <div align="center" style="margin-top: 10px">{{ verifyMessage }}</div>
    </el-dialog>
  </div>
</template>

<script>
import md5 from "js-md5";
import img0 from "@/assets/verifyImages/img0.jpg";
import img1 from "@/assets/verifyImages/img1.jpg";
import img2 from "@/assets/verifyImages/img2.jpg";
import img3 from "@/assets/verifyImages/img3.jpg";
import img4 from "@/assets/verifyImages/img4.jpg";
import img5 from "@/assets/verifyImages/img5.jpg";

export default {
  name: "Login",
  data() {
    return {
      // 登录需要输入的数据
      loginForm: {
        account: "admin",
        password: "111111",
      },
      // 登录框输入规则
      loginRules: {
        account: [{ required: true, message: "请输入账号", trigger: "blur" }],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 6, message: "密码最小为6位" },
        ],
      },
      loading: false,
      passwordType: "password",
      redirect: undefined,
      // 是否显示滑动验证框
      verifyDialog: false,
      // 滑动验证框信息
      verifyMessage: "",
      // 滑动验证框内部文字提示
      verifyText: "向右滑动滑块",
      // 滑动验证码的图片
      verifyImages: [img0, img1, img2, img3, img4, img5],
      // 消息框
      messageBox: {},
    };
  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true,
    },
  },
  created() {
    this.messageBox = this.$createElement;
  },
  methods: {
    // 是否显示密码
    showPassword() {
      if (this.passwordType === "password") {
        this.passwordType = "";
      } else {
        this.passwordType = "password";
      }
      this.$nextTick(() => {
        this.$refs.password.focus();
      });
    },
    // 登录使用的函数
    handleLogin() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          // 提示信息
          this.$notify({
            title: "提示",
            message: this.messageBox(
              "i",
              { style: "color:teal" },
              "请滑动验证码"
            ),
            duration: 2000,
          });
          // 显示验证框
          this.verifyDialog = true;
        } else {
          this.$message.error("账号或密码不符合验证规定");
          return false;
        }
      });
    },
    // 发送登录请求
    tryLogin() {
      this.loading = true;
      // md5加密
      const data = { ...this.loginForm };
      data.password = md5(data.password);
      this.$store
        .dispatch("user/login", data)
        .then(() => {
          this.$router.push({ path: "/" });
          this.loading = false;
        })
        .catch(() => {
          this.loading = false;
        });
    },
    // 验证码成功
    onSuccess(times) {
      this.verifyMessage = `验证成功, 耗时${(times / 1000).toFixed(1)}s`;
      // 等待1s后再登录
      setTimeout(() => {
        this.verifyDialog = false;
        this.tryLogin();
      }, 1000);
    },
    // 验证码失败
    onFailure() {
      this.verifyMessage = "";
    },
    // 重新刷新验证码
    onRefresh() {
      this.verifyMessage = "";
    },
  },
};
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg: #283443;
$light_gray: #fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style>
.m-dialog{
  max-width: 400px !important;
}
</style>

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.login-container {
  position: fixed;
  height: 100%;
  width: 100%;
  background-image: url("../../assets/background.jpg");
  background-size: 100% 100%;
  /* 背景图垂直、水平均居中 */
  background-position: center center;
  /* 背景图不平铺 */
  background-repeat: no-repeat;
  /* 当内容高度大于图片高度时，背景图像的位置相对于viewport固定 */
  background-attachment: fixed;
  /* 让背景图基于容器大小伸缩 */
  background-size: cover;
  /* 设置背景颜色，背景图加载过程中会显示背景色 */
  background-color: #464646;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>
