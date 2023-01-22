<template>
  <div class="app-container">
    <!-- 导航栏 -->
    <Header />
    <!-- 文章内容 -->
    <div
      class="h-body-bottom h-padded-td-big animate__animated animate__fadeIn"
    >
      <div class="ui container">
        <!-- 博客显示 -->
        <BlogDetail :id="articleId" />
      </div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/Header";
import BlogDetail from "@/components/BlogDetail";

export default {
  name: "Blog",
  components: {
    Header,
    BlogDetail
  },
  // 数据区
  data() {
    return {
      // 要显示的文章id
      articleId: undefined
    };
  },
  // 渲染前执行
  created() {
    if (this.$route.query.id) {
      this.articleId = this.$route.query.id;
    }
  },
  // 方法区
  methods: {}
};
</script>

<style scoped></style>
