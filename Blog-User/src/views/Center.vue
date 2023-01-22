<template>
  <div class="m-center">
    <Header></Header>
    <!-- 个人中心 -->
    <div
      class="ui form d-web-info-box h-margin-show h-padded-td-huge"
      data-wow-duration="0.4s"
      style="max-width:400px"
    >
      <div class="d-web-user-info-box">
        <img
          :src="userInfo.avatar"
          class="avatar-image"
          width="80px"
          height="80px"
        />
      </div>
      <!-- 更换头像按钮 -->
      <div class="field">
        <div
          class="ui blue button m-center"
          style="width:100px !important;"
          @click="uploadAvatar"
        >
          更换头像
          <input type="file" ref="upload" style="display:none;" />
        </div>
      </div>
      <div class="field">
        <label>用户昵称</label>
        <input
          type="text"
          v-model="userInfo.nickname"
          name="account"
          placeholder="请输入昵称"
        />
      </div>
      <div class="field">
        <label>密码</label>
        <input
          type="password"
          v-model="userInfo.password"
          name="password"
          placeholder="请输入密码"
        />
      </div>
      <div class="field" v-if="errorMessage">
        <div class="m-center red"></div>
      </div>
      <div class="ui orange button m-center" @click="updateUserInfo()">
        更新信息
      </div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/Header";
import { getUserInfo } from "@/utils/cookie";
import ossAPI from "@/api/oss";
import userAPI from "@/api/user";

let inputElement = null;
export default {
  name: "Center",
  components: {
    Header
  },
  // 数据区
  data() {
    return {
      // 用户信息
      userInfo: {
        nickname: "",
        password: "",
        avatar: ""
      },
      // 错误信息
      errorMessage: ""
    };
  },
  // 渲染前执行
  created() {
    // 获取用户信息
    this.getUserInfo();
  },
  // 方法区
  methods: {
    // 获取用户信息
    getUserInfo() {
      // 从cookie中获取
      const userInfo = getUserInfo();
      if (userInfo) {
        this.userInfo.nickname = userInfo.nickname;
        this.userInfo.avatar = userInfo.avatar;
      }
    },
    // 更换头像
    uploadAvatar() {
      if (inputElement === null) {
        // 生成文件上传的控件
        inputElement = document.createElement("input");
        inputElement.setAttribute("type", "file");
        inputElement.style.display = "none";

        if (window.addEventListener) {
          inputElement.addEventListener("change", this.uploadFile, false);
        } else {
          inputElement.attachEvent("onchange", this.uploadFile);
        }

        document.body.appendChild(inputElement);
      }
      inputElement.click();
    },
    uploadFile(el) {
      if (el && el.target && el.target.files && el.target.files.length > 0) {
        const file = el.target.files[0];
        const size = file.size / 1024 / 1024;

        const sizeValid = size < 3;
        // 判断上传文件的大小
        if (!sizeValid) {
          this.$message.show({
            title: "系统提示",
            message: "上传头像图片大小不能超过 3MB!",
            type: "error",
            duration: 2000
          });
        } else if (file.type.indexOf("image") === -1) {
          // 如果不是图片格式
          this.$message.show({
            title: "系统提示",
            message: "请选择图片文件",
            type: "error",
            duration: 2000
          });
        } else {
          const that = this;
          const reader = new FileReader(); // 创建读取文件对象
          reader.readAsDataURL(file); // 发起异步请求，读取文件
          reader.onload = function() {
            // 数据传到后台
            // 上传到后台
            ossAPI.uploadImage(file).then(res => {
              that.userInfo.avatar = res.data;
            });
          };
        }
      }
    },
    // 更新用户信息
    updateUserInfo() {
      userAPI.updateUserInfo(this.userInfo).then(res => {
        this.$message.show({
          title: "系统提示",
          message: "修改成功",
          type: "success",
          duration: 2000
        });
      });
    }
  },
  // 销毁前执行
  beforeDestroy() {
    if (inputElement) {
      if (window.addEventListener) {
        inputElement.removeEventListener("change", this.onGetLocalFile, false);
      } else {
        inputElement.detachEvent("onchange", this.onGetLocalFile);
      }
      document.body.removeChild(inputElement);
      inputElement = null;
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

.button {
  max-width: 200px !important;
  margin-top: 1em !important;
  margin: auto;
  border-radius: 5px !important;
}
</style>
