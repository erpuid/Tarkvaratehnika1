<template>
    <div class="super_container">

        <!-- Header -->
        <header class="header">
            <div class="header_content d-flex flex-row align-items-center justify-content-center">

                <!-- Logo -->
                <div class="logo top_align">
                    <a href="#"></a>
                    <div>Your<span>Fitness</span></div>
                    <div>better life starts here</div>
                </div>
                <nav class="main_nav">
                    <ul class="d-flex flex-row align-items-center justify-content-start">
                        <li><router-link to="/">Home</router-link></li>
                        <li><router-link to="/about">About</router-link></li>
                        <li><router-link to="/login">Login</router-link></li>
                        <li><router-link to="/savetraining">Save Training</router-link></li>
                        <li><router-link to="/history">Workout History</router-link></li>
                        <li><router-link to="/createworkout">Create Workout</router-link></li>
                    </ul>
                </nav>
                <div class="header_right top_align d-flex flex-row align-items-center justify-content-start">
                    <div class="search_container">
                        <div class="search_form_container">
                            <form> action="#" class="search_form" id="search_form">
                                <label>
                                    <input type="text" class="search_input" required="required" />
                                </label>
                            </form>
                        </div>
                        <div class="search_button top_align">
                            <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" viewBox="0 0 512 512" enable-background="new 0 0 512 512" width="512px" height="512px">
                                <g>
                                    <path d="M495,466.2L377.2,348.4c29.2-35.6,46.8-81.2,46.8-130.9C424,103.5,331.5,11,217.5,11C103.4,11,11,103.5,11,217.5   S103.4,424,217.5,424c49.7,0,95.2-17.5,130.8-46.7L466.1,495c8,8,20.9,8,28.9,0C503,487.1,503,474.1,495,466.2z M217.5,382.9   C126.2,382.9,52,308.7,52,217.5S126.2,52,217.5,52C308.7,52,383,126.3,383,217.5S308.7,382.9,217.5,382.9z" fill="#FFFFFF"/>
                                </g>
                            </svg>
                        </div>
                    </div>
                </div>
            </div>
        </header>

        <!-- Menu -->

        <div class="menu">
            <div class="menu">
                <nav class="menu_nav">
                    <ul class="main" style="font-weight: bold">
                        <li><router-link to="/">Home</router-link></li>
                        <li><router-link to="/about">About</router-link></li>
                        <li><router-link to="/login">Login</router-link></li>
                        <li><router-link to="/savetraining">Save Training</router-link></li>
                        <li><router-link to="/history">Workout History</router-link></li>
                        <li><router-link to="/createworkout">Create Workout</router-link></li>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</template>