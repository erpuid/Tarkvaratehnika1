<template>
    <!-- Home Slider -->
    <div class="home_slider_container carousel slide" data-ride="carousel">
        <div class="owl-theme home_slider carousel-inner">

            <div class="carousel-item active">
                <img class="d-block w-100" src="/images/index.jpg" alt="First slide">
                <div class="slide_container">
                    <div class="container">
                        <div class="row">
                            <div class="col">
                                <div class="slide_content">
                                    <div class="slide_title">
                                        <div>Don't stop</div>
                                        <div>when you're tired.</div>
                                        <div>Stop when</div>
                                        <div>you’re <span>done</span></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <div class="carousel-item">
                <img class="d-block w-100" src="/images/plans.jpg" alt="second slide">
                <div class="slide_container">
                    <div class="container">
                        <div class="row">
                            <div class="col">
                                <div class="slide_content">
                                    <div class="slide_title">
                                        <div>Don't stop</div>
                                        <div>when you're tired.</div>
                                        <div>Stop when</div>
                                        <div>you’re <span>done</span></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <div class="carousel-item">
                <img class="d-block w-100" src="/images/ropes.jpg" alt="third slide">
                <div class="slide_container">
                    <div class="container">
                        <div class="row">
                            <div class="col">
                                <div class="slide_content">
                                    <div class="slide_title">
                                        <div>Don't stop</div>
                                        <div>when you're tired.</div>
                                        <div>Stop when</div>
                                        <div>you’re <span>done</span></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Home Slider Dots -->
        <div class="home_slider_dots_container">
            <div class="container">
                <div class="row">
                    <div class="col">
                        <div class="home_slider_dots">
                            <ul id="home_slider_custom_dots" class="home_slider_custom_dots d-flex flex-row align-items-start justify-content-start">
                                <li class="home_slider_custom_dot active">01.</li>
                                <li class="home_slider_custom_dot">02.</li>
                                <li class="home_slider_custom_dot">03.</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</template>

<script>
    import {init} from '@/custom.js'

    export default {
        name: 'home',
        methods: {
            init
        },
        mounted: function() {
            init()
        }
    }
</script>

<style scoped>


</style>