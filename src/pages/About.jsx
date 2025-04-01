import React from "react";
import { assets } from "../assets/assets_frontend/assets";

const About = () => {
  return (
    <div>
      <div className="text-center text-2xl pt-10 text-gray-500">
        <p>
          ABOUT <span className="text-gray-700 font-medium">US</span>
        </p>
      </div>

      <div className="my-10 flex flex-col md:flex-row gap-12">
        <img
          className="w-full md:max-w-[360px]"
          src={assets.about_image}
          alt=""
        />
        <div className="flex flex-col justify-center gap-6 md:w-2/4 text-sm text-gray-600">
          <p>
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Quasi
            aliquid deserunt quia, fuga vero, sunt possimus distinctio cumque
            quam voluptate maxime quae dicta amet animi perspiciatis tempore
            minima. Cupiditate, iure?
          </p>
          <p>
            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Id impedit
            odit eaque? Eius iure aperiam, repudiandae dolorem sequi tempora
            accusamus excepturi velit placeat quasi eligendi, maxime quam rerum
            voluptas enim.
          </p>
          <b className="text-gray-800">Our Vision</b>
          <p>
            Lorem ipsum dolor sit, amet consectetur adipisicing elit.
            Exercitationem minus at laboriosam natus cum, omnis voluptates quasi
            neque ut rem ipsa culpa dolor praesentium incidunt, tenetur amet
            illo maxime reprehenderit.
          </p>
        </div>
      </div>

      <div className="text-xl my-4">
        <p>
          WHY <span className="text-gray-700 font-semibold">CHOOSE US</span>
        </p>
      </div>
      <div className="flex flex-col md:flex-row mb-20">
        <div
          className="border px-10 md:px-16 py-8 sm:py-16 flex flex-col gap-5 text-[15px]
        hover:bg-blue-600 hover:text-white transition-all duration-300 text gray-600 cursor-pointer 
        "
        >
          <b>EFFICIENCY:</b>
          <p>
            Streamlined appointment scheduling that fits into your busy
            lifestyle.
          </p>
        </div>
        <div
          className="border px-10 md:px-16 py-8 sm:py-16 flex flex-col gap-5 text-[15px]
        hover:bg-blue-600 hover:text-white transition-all duration-300 text gray-600 cursor-pointer 
        "
        >
          <b>CONVENIENCE:</b>
          <p>
            Access to a network of trusted healthcare professionals in your
            area.
          </p>
        </div>
        <div
          className="border px-10 md:px-16 py-8 sm:py-16 flex flex-col gap-5 text-[15px]
        hover:bg-blue-600 hover:text-white transition-all duration-300 text gray-600 cursor-pointer 
        "
        >
          <b>PERSONALIATION:</b>
          <p>
            Tailored recommendations and reminders to help you stay on top of
            your health.
          </p>
        </div>
      </div>
    </div>
  );
};

export default About;
