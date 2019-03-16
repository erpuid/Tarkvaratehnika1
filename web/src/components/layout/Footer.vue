<template>
    <!--Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0.-->
    <footer class="footer">
        <div class="footer_bar">
            <div class="container">
                <div class="row">
                    <div class="col">
                        <div class="footer_bar_content d-flex flex-row align-items-center justify-content-start">
                            <div class="copyright"><!--DO NOT REMOVE-->
                                Copyright &copy;{{date}} All rights reserved | This template is made with <i class="fa fa-heart-o" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </footer>
</template>

<script>
    export default {
        data: function date() {
            return {
                date: new Date().getFullYear()
            }
        }
    }
</script>