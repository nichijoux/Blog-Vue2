<template>
  <div class="ui container">
    <!-- 文章详情头部 -->
    <div class="ui top attached">
      <div class="ui mini horizontal link list">
        <div class="item">
          <div class="content">
            <span class="header"
              ><i class="user outline red icon"></i>&nbsp;{{
                article.title
              }}</span
            >
          </div>
        </div>
        <div class="item">
          <i class="eye green icon"></i>&nbsp;{{ article.viewCount }}
        </div>
        <div class="item">
          <i class="edit blue icon"></i>&nbsp;
          {{ article.updateTime }}
        </div>
      </div>
    </div>
    <!-- 文章内容 -->
    <!-- 内容 -->
    <div class="ui stackable grid">
      <div class="fourteen wide column">
        <div class="ui segment h-boder-radius">
          <h1 class="ui dividing header">
            <div class="content">{{ article.title }}</div>
          </h1>
          <!-- 文章内容 -->
          <div
            id="articleContent"
            class="typo typo-selection js-toc-content h-padded-lr markdown-body"
            v-html="article.content"
          ></div>
          <br />
          <!-- 标签显示 -->
          <div class="ui">
            <a
              :href="'/tags?id=' + tag.id"
              class="ui label m-margin-tb-mid"
              :class="tagColor[Math.floor(tag.id / 1000) % tagColor.length]"
              v-for="tag in article.tagList"
              :key="tag.id"
            >
              <span>{{ tag.name }}</span>
            </a>
          </div>
          <!-- 版权标注 -->
          <div class="ui floating message">
            <p>文章作者：{{ article.author }}</p>
            <p>
              版权声明：本站文章等内容仅代表作者本人的个人观点，本站不保证文章等内容的真实性和有效性。转载请注明文章出处。
            </p>
          </div>
        </div>
      </div>
      <!-- 目录显示占用2/16 -->
      <div class="two wide column">
        <div class="h-mobile-hide" style="position: sticky;top: 100px;">
          <div style="width: 250px">
            <div class="js-toc"></div>
          </div>
        </div>
      </div>
    </div>
    <!-- 评论内容 -->
    <Comment
      :key="commentTimer"
      :articleId="id"
      :authorId="article.createBy"
      @transfer="getCommentTimer"
    />
  </div>
</template>

<script>
import articleAPI from "@/api/article";
import Comment from "@/components/Comment";
import markdownIt from "markdown-it";
import mathjax from "markdown-it-mathjax3";
import anchor from "markdown-it-anchor";
import tocbot from "tocbot";

export default {
  name: "BlogDetail",
  components: {
    Comment
  },
  // 数据区
  data() {
    return {
      // 文章详情
      article: {},
      // 标签的class颜色
      tagColor: ["orange", "teal", "purple", "blue", "yellow", "grey", "red"],
      // timer
      commentTimer: ""
    };
  },
  // 传递数据
  props: {
    id: {
      type: String
    }
  },
  // 渲染前执行
  created() {
    if (this.id) {
      this.getArticle(this.id);
    }
  },
  // 摧毁前
  beforeDestroy() {
    tocbot.destroy();
  },
  // 方法区
  methods: {
    getArticle(id) {
      articleAPI.getPublishedArticleDetail(id).then(res => {
        this.article = res.data;
        const markdown = new markdownIt();
        markdown.use(mathjax);
        markdown.use(anchor);
        this.article.content = markdown.render(this.article.content);
        // 生成目录
        this.$nextTick(() => {
          // 生成目录
          tocbot.init({
            // Where to render the table of contents.
            tocSelector: ".js-toc",
            // Where to grab the headings to build the table of contents.
            contentSelector: ".js-toc-content",
            // Which headings to grab inside of the contentSelector element.
            headingSelector: "h1,h2,h3",
            // For headings inside relative or absolute positioned containers within content.
            hasInnerContainers: true,
            headingsOffset: 60,
            scrollSmoothOffset: -60
          });
        });
      });
    },
    // 获取时间
    getCommentTimer(timer) {
      this.commentTimer = timer;
    }
  }
};
</script>

<style scoped>
.js-toc > ol {
  list-style: none;
  counter-reset: li;
  padding-left: 1.5rem;
}
.js-toc > ol > li {
  font-weight: 700;
  padding-bottom: 0.25rem;
}
.js-toc > ol > li.is-active-li > .node-name--H1,
.js-toc > ol > li.is-active-li > .node-name--H2,
.js-toc > ol > li.is-active-li > .node-name--H3 {
  color: var(--text-accent);
}
.js-toc > ol ol li {
  font-weight: 600;
  margin-top: 0.375rem;
  margin-bottom: 0.375rem;
  padding-left: 1.5rem;
}
.js-toc > ol ol li.is-active-li > .node-name--H2,
.js-toc > ol ol li.is-active-li > .node-name--H3 {
  color: var(--text-accent);
}
.js-toc > ol ol li ol li {
  font-weight: 500;
  margin-top: 0.375rem;
  margin-bottom: 0.375rem;
  padding-left: 1.5rem;
}
.js-toc > ol ol li ol li.is-active-li .node-name--H3 {
  color: var(--text-accent);
}
.js-toc > ol ol,
.js-toc > ol ol ol {
  position: relative;
}
.js-toc > ol ol ol ol ol > li:before,
.js-toc > ol ol ol ol > li:before,
.js-toc > ol ol ol > li:before,
.js-toc > ol ol > li:before,
.js-toc > ol > li:before {
  content: "";
  color: var(--text-accent);
  display: inline-block;
  width: 1em;
  margin-left: -1.15em;
  padding: 0;
  font-weight: 700;
  text-shadow: 0 0 0.5em var(--accent-2);
}
.js-toc > ol > li:before {
  font-size: 1.25rem;
  line-height: 1.75rem;
}
.js-toc > ol > li > ol:before,
.js-toc > ol > li > ol > li > ol:before {
  content: "";
  border-left: 1px solid var(--text-accent);
  position: absolute;
  opacity: 0.35;
  left: -1em;
  top: 0;
  bottom: 0;
}
.js-toc > ol > li > ol:before {
  left: -1.25em;
  border-left: 2px solid var(--text-accent);
}
</style>
