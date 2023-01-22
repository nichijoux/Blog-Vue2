<template>
  <div
    class="h-padded h-fixed h-right-bottom"
    style="display: none"
    @click="backToTop()"
  >
    <a title="回到顶部" href="javascript:" class="ui icon button top-button"
      ><i class="angle up blue icon"></i
    ></a>
  </div>
</template>

<script>
export default {
  name:'BackToTop',
  // 数据区
  data() {
    return {};
  },
  // 渲染前执行
  created() {},
  // 方法区
  methods: {
    backToTop() {
      $(window).scrollTo(0, 500);
    }
  }
};
</script>

<style scoped></style>
