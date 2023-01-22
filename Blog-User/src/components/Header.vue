<template>
  <div class="ui top fixed menu m-header">
    <nav class="h-shadow-small ui attached segment h-padded-td-mini">
      <!-- 滚轮进度条 -->
      <div
        class="ui bottom indicating attached progress"
        data-value="1"
        data-total="100"
      >
        <div class="bar"></div>
      </div>
      <div class="ui secondary stackable menu navigation_height">
        <h2 class="ui left header item">
          <a href="/" style="color:black">
            <div class="content">
              nichijoux<span style="font-size: 20px">的博客</span>
            </div>
          </a>
        </h2>
        <!-- 搜索框 -->
        <div class="ui item" :class="{ 'h-mobile-hide': mobileHide }">
          <div class="ui item category search">
            <div class="ui icon input">
              <input class="prompt" type="text" placeholder="搜索文章" />
              <i class="search link icon"></i>
            </div>
            <div class="results"></div>
          </div>
        </div>
        <!-- 导航框 -->
        <a
          href="/index"
          class="ui item navigation-button"
          :class="{ 'h-mobile-hide': mobileHide }"
          ><i class="home icon"></i>首页</a
        >
        <!-- 标签页 -->
        <a
          href="/tags"
          class="ui item navigation-button"
          :class="{ 'h-mobile-hide': mobileHide }"
          ><i class="tags icon"></i>标签</a
        >
        <!-- 归档页 -->
        <a
          href="/archive"
          class="ui item navigation-button"
          :class="{ 'h-mobile-hide': mobileHide }"
          ><i class="calendar alternate icon"></i>归档</a
        >
        <!-- 友链页 -->
        <a
          href="/link"
          class="ui item navigation-button"
          :class="{ 'h-mobile-hide': mobileHide }"
          ><i class="linkify icon"></i>友链</a
        >
        <!-- 关于我 -->
        <a
          href="/about"
          class="ui item navigation-button"
          :class="{ 'h-mobile-hide': mobileHide }"
          ><i class="address card outline icon"></i>关于我</a
        >
        <!-- 登录/注册 -->
        <a
          href="/login"
          v-if="!userInfo"
          class="ui item navigation-button"
          :class="{ 'h-mobile-hide': mobileHide }"
          ><i class="external square alternate icon"></i>登录</a
        >
        <!-- 用户信息 -->
        <div class="ui simple dropdown" v-if="userInfo">
          <div
            class="ui item navigation-button"
            :class="{ 'h-mobile-hide': mobileHide }"
          >
            <img :src="userInfo.avatar" /><i class="dropdown icon"></i>
          </div>

          <div class="menu">
            <div class="item" @click="userCenter()">个人中心</div>
            <div class="item" @click="logout()">退出登录</div>
          </div>
        </div>
      </div>
      <a
        href="#"
        class="ui icon button h-right-top-storage h-mobile-show"
        style="background-color: #ffffff;"
        @click="switchNavBar()"
      >
        <i class="sidebar icon"></i>
      </a>
    </nav>
  </div>
</template>

<script>
import { getUserInfo, removeToken, removeUserInfo } from "@/utils/cookie";
import loginAPI from "@/api/login";

export default {
  name: "Header",
  // 数据区
  data() {
    return {
      // 手机端是否显示
      mobileHide: true,
      // 用户信息
      userInfo: undefined
    };
  },
  // 渲染前执行
  created() {
    this.userInfo = getUserInfo();
  },
  // 方法区
  methods: {
    // 转换导航栏的显示
    switchNavBar() {
      // 设置元素是否显示
      this.mobileHide = !this.mobileHide;
    },
    // 跳转到个人中心
    userCenter() {
      // 跳转到个人中心
      this.$router.push("/center");
    },
    // 退出登录
    logout() {
      loginAPI.logout().then(res => {
        removeToken();
        removeUserInfo();
        // 刷新页面
        this.userInfo = getUserInfo();
        // 提示信息
        this.$message.show({
          title: "系统提示",
          message: "退出登录成功",
          type: "success",
          duration: 2000
        });
      });
    }
  }
};
</script>

<style scoped>
@media only screen and (max-width: 1010px) {
  .h-mobile-show {
    display: block !important;
  }
  .h-mobile-hide {
    display: none !important;
  }
  .ui.stackable.menu {
    -webkit-box-orient: vertical !important;
    -webkit-box-direction: normal !important;
    -ms-flex-direction: column !important;
    flex-direction: column !important;
  }
}
</style>
