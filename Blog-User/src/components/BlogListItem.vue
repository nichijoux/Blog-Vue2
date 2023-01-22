<template>
  <!-- 每篇文章都是一个box -->
  <div class="d-web-info-box" data-wow-duration="0.4s">
    <div class="ui mobile stackable grid">
      <!-- 图片 -->
      <div class="seven wide column" style="overflow: hidden;">
        <img
          class="ui rounded image centered item-image"
          :src="article.cover"
        />
      </div>
      <!-- 文章信息 -->
      <div class="nine wide column h-padded-td-large">
        <!-- 文章标题 -->
        <a :href="'/blog?id=' + article.id"
          ><h2 class="ui header title-suspension">
            {{ article.title }}
          </h2></a
        >
        <!-- 文章浏览详情 -->
        <div class="ui grid">
          <div class="wide column">
            <div class="ui mini horizontal link list">
              <!-- 作者 -->
              <div class="item">
                <div class="content">
                  <span class="header"
                    ><i class="user outline red  icon"></i
                    >{{ article.author }}</span
                  >
                </div>
              </div>
              <!-- 创建时间 -->
              <div class="item">
                <i class="clock outline blue icon"></i>{{ article.createTime }}
              </div>
              <!-- 浏览数 -->
              <div class="item">
                <i class="eye green icon"></i>
                {{ article.viewCount }}
              </div>
            </div>
          </div>
        </div>
        <!-- 文章摘要 -->
        <p class="m-text">
          {{ article.summary }}
        </p>
        <!-- 文章标签 -->
        <div class="h-padded-td-small label-list">
          <!-- 标签 -->
          <div class="ui">
            <a
              :href="'/tags?id=' + tag.id"
              v-for="tag in article.tagList"
              :key="tag.id"
              class="ui left label m-magin-tb-mid"
              :class="tagColor[Math.floor(tag.id / 1000) % tagColor.length]"
              ><span>{{ tag.name }}</span></a
            >
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "BlogListItem",
  // 数据区
  data() {
    return {
      // 标签的class颜色
      tagColor: ["orange", "teal", "purple", "blue", "yellow", "grey", "red"]
    };
  },
  // 传递数据
  props: {
    article: {}
  }
};
</script>
