<template>
  <div class="app-container">
    <!-- 导航 -->
    <Header />
    <!-- 背景 -->
    <BackgroundImage :url="'/static/image/Hangmoon.full.jpg'" />
    <!-- 主体 -->
    <div class="h-padded-td-big" id="index-body" ref="indexBody">
      <div class="ui container">
        <div class="ui stackable grid">
          <div class="eleven wide column">
            <!--  文章列表 -->
            <div
              v-for="article in articleList"
              :key="article.id"
              class="ui attached animate__animated animate__fadeInUp"
            >
              <!-- 每篇文章都是一个BlogListItem -->
              <BlogListItem :article="article" />
            </div>
            <!-- 分页栏 -->
            <div class="ui middle aligned centered grid h-padded-td">
              <div class="ui pagination menu" v-if="pageVO.total != 0">
                <a
                  class="item"
                  v-if="pageVO.hasPrevious"
                  @click="previousPage()"
                  >上一页&nbsp;&nbsp;&nbsp;</a
                >
                <a
                  v-for="page in pageVO.total"
                  :key="page"
                  class="item"
                  :class="{ active: pageVO.current == page }"
                  @click="pageChange(page)"
                >
                  {{ page }}
                </a>
                <a class="item" v-if="pageVO.hasNext" @click="nextPage()"
                  >&nbsp;&nbsp;&nbsp;下一页</a
                >
              </div>
            </div>
          </div>
          <!-- 	侧边栏 -->
          <SideBar />
        </div>
      </div>
    </div>
    <!-- 回到顶部 -->
    <BackToTop />
    <!-- footer -->
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/Header";
import BackgroundImage from "@/components/BackgroundImage";
import SideBar from "@/components/SideBar";
import BlogListItem from "@/components/BlogListItem";
import BackToTop from "@/components/BackToTop";
import Footer from "@/components/Footer";
import articleAPI from "@/api/article";

export default {
  name: "Home",
  components: {
    Header,
    BackgroundImage,
    SideBar,
    BlogListItem,
    BackToTop,
    Footer
  },
  // 数据区
  data() {
    return {
      // 文章列表
      articleList: [],
      // 标签的class颜色
      tagColor: ["orange", "teal", "purple", "blue", "yellow", "grey", "red"],
      // 当前页
      index: 1,
      // 每页记录数
      limit: 5,
      // pageVO
      pageVO: {}
    };
  },
  // 渲染前执行
  created() {
    this.getArticleList(this.index);
  },
  // 方法区
  methods: {
    // 分页查询文章
    getArticleList(index) {
      this.index = index;
      articleAPI.pageQueryPublishedArticle(this.index, this.limit).then(res => {
        this.articleList = res.data.records;
        this.index = Number(res.data.current);
        this.pageVO.current = Number(res.data.current);
        this.pageVO.total = parseInt(res.data.total / this.limit);
        this.pageVO.hasPrevious = res.data.hasPrevious;
        this.pageVO.hasNext = res.data.hasNext;
      });
    },
    // 前一页
    previousPage() {
      if (this.index >= 1) {
        this.getArticleList(this.index - 1);
      }
    },
    // 页面改变
    pageChange(page) {
      if (1 <= page && page <= this.pageVO.total) {
        this.getArticleList(page);
      }
    },
    // 后一页
    nextPage() {
      if (this.index < this.pageVO.total) {
        this.getArticleList(this.index + 1);
      }
    }
  }
};
</script>

<style scoped></style>
