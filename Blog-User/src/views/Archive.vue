<template>
  <div class="app-container">
    <!-- 导航 -->
    <Header></Header>
    <!-- 主体 -->
    <div class="h-body-bottom h-margin-show h-padded-td-huge">
      <div class="ui container">
        <!-- 归档 -->
        <ul class="layui-timeline">
          <li
            class="layui-timeline-item animate__animated animate__fadeIn"
            v-for="archiveData in archiveList"
            :key="archiveData.year"
          >
            <i
              class="layui-timeline-axis calendar alternate icon"
              style="background-color: #f5f5f5 !important;"
            ></i>
            <!-- 每一年份的归档数据 -->
            <div class="layui-timeline-content layui-text">
              <!-- 年份 -->
              <h2 class="layui-timeline-title">
                <b class="h-box-margin-left">{{ archiveData.year }}</b>
              </h2>
              <!-- 年份下的数据 -->
              <div>
                <div
                  class="ui fluid vertical menu h-boder-radius animate__animated animate__flipInX "
                  v-for="article in archiveData.articleList"
                  :key="article.id"
                >
                  <a
                    :href="'/blog?id=' + article.id"
                    class="item"
                    style="text-decoration:none"
                  >
                    <span>
                      <div
                        class="ui green basic left pointing label h-padded-miniplus"
                      >
                        {{ article.createTime }}
                      </div>
                      <b class="h-box-margin-left">{{ article.title }}</b>
                    </span>
                  </a>
                </div>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
    <!-- 回到顶部 -->
    <BackToTop></BackToTop>
  </div>
</template>

<script>
import Header from "@/components/Header";
import BackToTop from "@/components/BackToTop";
import articleAPI from "@/api/article";

export default {
  components: {
    Header,
    BackToTop
  },
  // 数据区
  data() {
    return {
      // 归档数据
      archiveList: []
    };
  },
  // 渲染前执行
  created() {
    this.getArchiveList();
  },
  // 方法区
  methods: {
    // 获取归档列表
    getArchiveList() {
      articleAPI.getArchiveList().then(res => {
        this.archiveList = res.data;
      });
    }
  }
};
</script>

<style scoped>
.h-body-bottom {
  margin: auto !important;
  margin-top: 66px !important;
}
.h-boder-radius {
  border-radius: 10px !important;
}
.h-box-margin-left {
  margin-left: 10px !important;
}
</style>
