<template>
  <div id="app">
    <Header id="header"/>
    <div class="home">
      <router-view/>
    </div>
    <Footer id="footer"/>
  </div>
</template>

<script>
    import Header from './components/layout/Header';
    import Footer from './components/layout/Footer';

    export default {
        name: 'app',
        components: {
            Header,
            Footer
        }
    }
</script>


<style>
  #app {
    display: flex;
    flex-direction: column;
    min-height: 100vh;
  }

  .home {
    flex: 1;
  }

  #header {
    height: 124px;
  }
  #footer {
    height: 53px;
  }

</style>
