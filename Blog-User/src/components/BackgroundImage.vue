<template>
  <div
    class="background"
    :style="{
      backgroundImage: 'url(' + url + ')'
    }"
  >
    <div class="h-bg-text">
      <center>
        <p id="p-title" class="text-animation">
          {{ saying }}
        </p>
      </center>
    </div>
    <div class="h-bg-text">
      <a
        class="h-text-font-size-plus"
        id="scroll_down_button"
        v-scroll-to="'#index-body'"
      >
        <i
          class="angle down icon animate__animated animate__bounce"
          style="color: rgb(255, 255, 255); caret-color: rgb(255, 255, 255);"
        ></i>
      </a>
    </div>
  </div>
</template>

<script>
export default {
  name: "BackgroundImage",
  // 数据区
  data() {
    return {
      // 显示的句子
      saying: ""
    };
  },
  // 所需参数
  props: {
    url: {
      type: String
    },
  },
  // 渲染前执行
  created() {
    this.getSaying();
  },
  // 方法区
  methods: {
    // 随机获取一条句子
    getSaying() {
      this.$axios
        .get("https://v1.hitokoto.cn/?Cati=d&max_length=25")
        .then(res => {
          this.saying = res.data.hitokoto;
        });
    },
  }
};
</script>

<style scoped>
.background {
  margin-top: 60px;
  background: center center / cover no-repeat;
}
</style>
