<template>
  <div class="five wide column animate__animated animate__fadeIn">
    <!-- 基本信息 -->
    <div class="d-web-info-box" data-wow-duration="0.4s">
      <div class="d-web-user-info-box">
        <img
          src="/static/image/blogAvatar.jpg"
          class="avatar-image"
          width="80px"
          height="80px"
        />
        <h3>nichijoux</h3>
        <span>往者不可谏,来者犹可追</span>
      </div>
      <div class="ui aligned centered grid h-padded-td-large">
        <span class="m-text"
          >文章<br /><a class="m-text-span statistic">{{
            articleCount
          }}</a></span
        >
        <span class="m-text"
          >标签<br /><a class="m-text-span statistic">{{ tagCount }}</a></span
        >
        <span class="m-text"
          >评论<br /><a class="m-text-span statistic">{{
            commentCount
          }}</a></span
        >
        <span class="m-text"
          >浏览量<br /><a class="m-text-span statistic">{{
            viewCount
          }}</a></span
        >
      </div>
      <div class="h-padded-td-small">
        <span><i class="rocket icon"></i>社交</span>
      </div>
      <br />
      <div class="ui aligned centered middle grid h-padded-td">
        <p class="h-text-font-size">
          <i class="wechat green icon"></i
          ><a th:href="@{/img/WechatQRCode.jpg}" target="_blank">&nbsp;微信</a>
        </p>
        <p class="h-text-font-size">
          <i class="copyright orange icon"></i
          ><a href="https://blog.csdn.net/qq_53282665" target="_blank"
            >&nbsp;CSDN</a
          >
        </p>
        <p class="h-text-font-size">
          <i class="github square black icon"></i
          ><a href="https://github.com/nichijoux" target="_blank"
            >&nbsp;GitHub</a
          >
        </p>
      </div>
      <!-- 标签 -->
      <div class="h-padded-td-small">
        <span><i class="tags icon"></i>标签</span>
      </div>
      <!-- 标签列表显示 -->
      <div class="h-padded-td-small">
        <!-- 标签 -->
        <div class="ui">
          <a
            :href="'/tag?id=' + tag.id"
            v-for="tag in tagList"
            :key="tag.id"
            class="ui left label m-magin-tb-mid"
            :class="tagColor[Math.floor(tag.id / 1000) % tagColor.length]"
            ><span>{{ tag.name }}</span></a
          >
        </div>
      </div>
      <!-- 显示网站运行时间 -->
      <div class="h-padded-td-small">
        <span><i class="globe icon"></i>网站运行时间</span>
      </div>
      <!-- 显示网站运行时间 -->
      <div class="h-padded-td-small">
        <span>{{ runTime }}</span>
      </div>
    </div>
    <!-- 推荐文章 -->
    <div class="d-web-info-box" data-wow-duration="0.4s">
      <!-- 推荐文章文字 -->
      <div class="h-padded-td-small">
        <span><i class="blogger b icon"></i>推荐文章</span>
      </div>
      <!-- 真正推荐的文章 -->
      <div class="ui aligned centered middle grid h-padded-td">
        <div
          v-for="article in recommandArticleList"
          :key="article.id"
          class="recommand-article"
        >
          <a :href="'/blog?id=' + article.id" style="color:black">
            <span class="h-text-font-size">
              {{ article.title }}
            </span>
          </a>
          ——<span>浏览数:{{ article.viewCount }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import statisticsAPI from "@/api/statistics";
import tagAPI from "@/api/tag";
import articleAPI from "@/api/article";

export default {
  name: "SideBar",
  // 数据区
  data() {
    return {
      // 统计数据区域
      articleCount: 0,
      tagCount: 0,
      commentCount: 0,
      viewCount: 0,
      // 标签列表
      tagList: [],
      // 标签的class颜色
      tagColor: ["orange", "teal", "purple", "blue", "yellow", "grey", "red"],
      // 热门推荐文章
      recommandArticleList: [],
      // 网站创建时间(Date的Month取值范围为0~11)
      createTime: Math.round(new Date(2022, 10, 1, 12, 0, 0).getTime() / 1000),
      // 运行时间
      runTime: "",
      // 运行时间计算器
      runTimeTimer: undefined
    };
  },
  // 渲染前执行
  created() {
    // 获取统计数据
    this.getStatistics();
    // 获取所有的标签
    this.getAllEnableTag();
    // 获取推荐文章
    this.getHotArticleList();
  },
  // 挂载后执行
  mounted() {
    this.runTimeTimer = setInterval(() => {
      this.calculateRunTime();
    }, 1000);
  },
  // 销毁前执行
  destroyed() {
    clearInterval(this.runTimeTimer);
  },
  // 方法区
  methods: {
    // 获取统计数据
    getStatistics() {
      statisticsAPI.getStatistics().then(res => {
        this.articleCount = res.data.articleCount;
        this.tagCount = res.data.tagCount;
        this.commentCount = res.data.commentCount;
        this.viewCount = res.data.viewCount;
      });
    },
    // 获取所有启用的标签
    getAllEnableTag() {
      tagAPI.getAllEnableTag().then(res => {
        this.tagList = res.data;
      });
    },
    // 获取推荐文章
    getHotArticleList() {
      articleAPI.getHotArticleList().then(res => {
        this.recommandArticleList = res.data;
      });
    },
    // 网站运行时间
    calculateRunTime() {
      // 当前时间
      let timestamp = Math.round(new Date().getTime() / 1000);
      let currentTime = this.secondToDate(timestamp - this.createTime);
      let currentTimeText =
        "本站已运行：" +
        currentTime[0] +
        "年" +
        currentTime[1] +
        "天" +
        currentTime[2] +
        "时" +
        currentTime[3] +
        "分" +
        currentTime[4] +
        "秒";
      this.runTime = currentTimeText;
    },
    // 将秒转换为year、day、hour、minute、second格式
    secondToDate(second) {
      if (!second || second < 0) {
        return 0;
      }
      let time = new Array(0, 0, 0, 0, 0);
      if (second >= 365 * 24 * 3600) {
        time[0] = parseInt(second / (365 * 24 * 3600));
        second %= 365 * 24 * 3600;
      }
      if (second >= 24 * 3600) {
        time[1] = parseInt(second / (24 * 3600));
        second %= 24 * 3600;
      }
      if (second >= 3600) {
        time[2] = parseInt(second / 3600);
        second %= 3600;
      }
      if (second >= 60) {
        time[3] = parseInt(second / 60);
        second %= 60;
      }
      if (second > 0) {
        time[4] = second;
      }
      return time;
    }
  }
};
</script>

<style scoped>
.h-text-font-size:hover {
  color: rgba(113, 113, 214, 0.861);
}
.statistic {
  color: black;
}
</style>
