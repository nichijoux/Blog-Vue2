<template>
  <div class="m-center">
    <Header />
    <div
      class="ui form d-web-info-box h-margin-show h-padded-td-huge"
      data-wow-duration="0.4s"
      style="max-width:400px"
    >
      <!-- 登录注册 -->
      <div class="title">
        <a :class="{ active: showLoginForm }" @click="showForm(true)">
          登录
        </a>
        <span>·</span>
        <a :class="{ active: !showLoginForm }" @click="showForm(false)">
          注册
        </a>
      </div>
      <!-- 登录表单 -->
      <div class="login" v-if="showLoginForm">
        <div class="field">
          <label>账号</label>
          <input
            type="text"
            v-model="loginDTO.account"
            name="account"
            placeholder="请输入账号"
          />
        </div>
        <div class="field">
          <label>密码</label>
          <input
            type="password"
            v-model="loginDTO.password"
            name="password"
            placeholder="请输入密码"
          />
        </div>
        <div class="ui orange button m-center" @click="login()">登录</div>
        <div class="ui teal button m-center" @click="goBackHome()">
          返回首页
        </div>
      </div>
      <!-- 注册表单 -->
      <div class="register" v-else>
        <!-- 账号 -->
        <div class="field">
          <label>账号</label>
          <input
            type="text"
            v-model="registerDTO.account"
            name="account"
            placeholder="请输入账号"
          />
        </div>
        <!-- 密码 -->
        <div class="field">
          <label>密码</label>
          <input
            type="password"
            v-model="registerDTO.password"
            name="password"
            placeholder="请输入密码"
          />
        </div>
        <!-- 用户昵称 -->
        <div class="field">
          <label>用户昵称</label>
          <input
            type="text"
            v-model="registerDTO.nickname"
            name="nickname"
            placeholder="请输入用户昵称"
          />
        </div>
        <!-- 邮箱 -->
        <div class="field" style="position:relative;">
          <label>用户邮箱</label>
          <input
            type="email"
            v-model="registerDTO.email"
            name="email"
            placeholder="请输入用户邮箱"
          />
          <a
            type="button"
            class="validate-code"
            @click="getValidateCode"
            :value="codeText"
            style="border: none; background-color: none"
            >{{ codeText }}</a
          >
        </div>
        <!-- 验证码 -->
        <div class="field" style="position:relative;">
          <label>验证码</label>
          <input
            type="text"
            v-model="registerDTO.code"
            name="code"
            placeholder="请输入注册验证码"
            maxlength="6"
          />
        </div>
        <!-- 注册账号 -->
        <div class="ui orange button m-center" @click="register()">注册</div>
        <!-- 返回首页 -->
        <div class="ui teal button m-center" @click="goBackHome()">
          返回首页
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/Header";
import loginAPI from "@/api/login";
import smsAPI from "@/api/sms";
import md5 from "js-md5";
import { setToken, setUserInfo } from "@/utils/cookie";

export default {
  components: { Header },
  // 数据区
  data() {
    return {
      // 是否显示登录表单
      showLoginForm: true,
      // 登录数据
      loginDTO: {
        account: "",
        password: ""
      },
      // 注册数据
      registerDTO: {
        account: "",
        password: "",
        nickname: "",
        email: "",
        code: ""
      },
      // 是否发送验证码
      sending: true,
      // 倒计时间
      second: 60,
      // 文字
      codeText: "获取验证码"
    };
  },
  // 渲染前执行
  created() {},
  // 方法区
  methods: {
    // 登录
    login() {
      // 密码加密
      let loginDTO = { ...this.loginDTO };
      loginDTO.password = md5(loginDTO.password);
      // 调用API进行登录
      loginAPI.login(loginDTO).then(res => {
        // 存入cookie和token
        setToken(res.data.token);
        setUserInfo(res.data.userInfo);
        // 回首页
        window.location.href = "/";
      });
    },
    // 获取验证码
    getValidateCode() {
      if (!this.sending) return;
      const reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
      if (reg.test(this.registerDTO.email)) {
        smsAPI.getValidateCode(this.registerDTO.email).then(res => {
          this.sending = false;
          this.timeDown();
        });
      } else {
        // 错误提示
        this.$message.show({
          title: "系统提示",
          message: "邮箱格式错误",
          type: "error",
          duration: 2000
        });
      }
    },

    // 倒计时
    timeDown() {
      let result = setInterval(() => {
        --this.second;
        this.codeText = this.second;
        if (this.second < 1) {
          clearInterval(result);
          this.sending = true;
          this.second = 60;
          this.codeText = "获取验证码";
        }
      }, 1000);
    },
    // 注册
    register() {
      let registerData = { ...this.registerDTO };
      registerData.password = md5(registerData.password);
      // 调用API进行注册
      loginAPI.register(registerData).then(res => {
        // 注册成功,则跳转到登录页面
        this.showLoginForm = true;
      });
    },
    // 显示
    showForm(bool) {
      this.showLoginForm = bool;
    },
    // 返回首页
    goBackHome() {
      window.location.href = "/";
    }
  }
};
</script>

<style scoped>
.m-center {
  display: flex;
  justify-content: center;
  align-items: center;
}

.h-padded-td-huge {
  margin-top: 66px !important;
  padding-bottom: 20px !important;
}

.red {
  color: red;
}

input {
  background-color: #f1f1f1 !important;
}

.button {
  max-width: 200px !important;
  margin-top: 1em !important;
  margin: auto;
}

.title {
  margin: 0 auto 50px;
  padding: 10px;
  text-align: center;
}

.title a {
  padding: 10px;
  color: #969696;
  font-size: 18px;
}

.title a:hover {
  border-bottom: 2px solid #3bb149;
}

.title .active {
  font-weight: 700;
  color: #3bb149;
  border-bottom: 2px solid #3bb149;
}

.title span {
  padding: 10px;
  color: #969696;
  font-weight: 700;
}

.validate-code {
  position: absolute;
  right: 0px;
  top: 28px;
  width: 40%;
  max-width: 80px;
  border: none;
}

.register-message {
  font-size: 18px;
  text-align: center;
  font-weight: 700;
  color: #3bb149;
}
</style>
