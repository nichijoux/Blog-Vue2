<template>
  <div class="app-container">
    <!-- 导航栏 -->
    <Header />
    <!-- 内容 -->
    <div id="index-body" class="h-body-bottom h-margin-show h-padded-td-large">
      <div class="ui container">
        <!-- 标签 -->
        <div
          class="d-web-info-box animate__animated animate__fadeIn"
          v-if="!tagId"
        >
          <center>
            <h1>标签-{{ tagList.length }}</h1>
          </center>
          <div
            class="ui h-padded-td-large aligned centered middle grid h-boder-radius"
          >
            <div
              v-for="tag in tagList"
              :key="tag.id"
              class="ui labeled button h-margin-td-tiny"
              style="margin-left: 15px"
            >
              <a
                :href="'/tags?id=' + tag.id"
                class="ui tags-button"
                :style="
                  'font-size:' +
                    Math.round(Math.random() * (30 - 15) + 15) +
                    'px'
                "
                >{{ tag.name }}</a
              >
            </div>
          </div>
        </div>
        <!-- 搜索内容 -->
        <div
          v-for="article in articleList"
          :key="article.id"
          class="ui attached animate__animated animate__fadeInUp"
        >
          <BlogListItem :article="article" />
        </div>
        <!-- 分页栏 -->
        <div class="ui middle aligned centered grid h-padded-td">
          <div class="ui pagination menu" v-if="pageVO.total > 1">
            <a class="item" v-if="pageVO.hasPrevious" @click="previousPage()"
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
    </div>
  </div>
</template>

<script>
import Header from "@/components/Header";
import BlogListItem from "@/components/BlogListItem";
import tagAPI from "@/api/tag";
import articleAPI from "@/api/article";

export default {
  name: "Tags",
  components: {
    Header,
    BlogListItem
  },
  // 数据区
  data() {
    return {
      // 搜索的文章
      articleList: [],
      // 标签列表
      tagList: [],
      // 搜索的标签id
      tagId: undefined,
      // 当前页
      index: 1,
      limit: 5,
      // pageVO
      pageVO: {}
    };
  },
  // 渲染前执行
  created() {
    // 获取标签
    if (this.$route.query.id) {
      this.tagId = this.$route.query.id;
      // 根据tagId获取文章
      this.getArticleList(this.index);
    }
    if (!this.tagId) {
      this.getTagList();
    }
  },
  // 方法区
  methods: {
    // 获取标签
    getTagList() {
      tagAPI.getAllEnableTag().then(res => {
        this.tagList = res.data;
      });
    },
    // 分页查询文章
    getArticleList(index) {
      this.index = index;
      articleAPI
        .pageQueryPublishedArticle(this.index, this.limit, this.tagId)
        .then(res => {
          this.articleList = res.data.records;
          this.index = Number(res.data.current);
          this.pageVO.current = Number(res.data.current);
          // 计算总页数
          if (res.data.total % this.limit == 0) {
            // 如果刚好取余为整则
            this.pageVO.total = parseInt(res.data.total / this.limit);
          } else {
            // 否则
            this.pageVO.total = Math.floor(res.data.total / this.limit) + 1;
          }
          // 是否有上下页
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
