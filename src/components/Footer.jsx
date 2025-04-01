import React from "react";
import { assets } from "../assets/assets_frontend/assets";

const Footer = () => {
  return (
    <div className="md:mx-10">
      <div
        className="flex flex-col sm:grid grid-cols-[3fr_1fr_1fr]
      gap-14 my-10 mt-40 text-sm
      "
      >
        {/*Left section */}
        <div>
          <img className="mb-5 w-40" src={assets.logo} />
          <p className="w-full md:w-2/3 text-gray-600 leading-6">
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Recusandae
            doloribus accusantium, non maxime at soluta molestias. Possimus
            aperiam, labore dolorum incidunt deserunt perspiciatis, similique
            magni tenetur atque omnis eius totam?
          </p>
        </div>

        {/*Center section */}
        <div>
          <p className="text-xl font-medium mb-5">COMPANY</p>
          <ul className="flex flex-col gap-2 text-gray-600">
            <li>Home</li>
            <li>About Us</li>
            <li>Contact Us</li>
            <li>Privacy Policy</li>
          </ul>
        </div>

        {/*Right section */}
        <div>
          <p className="text-xl font-medium mb-5">GET IN TOUCH</p>
          <ul className="flex flex-col gap-2 text-gray-600">
            <li>0777717177</li>
            <li>jeyarubanjenushan3@gmail.com</li>
          </ul>
        </div>
      </div>
      {/*Copy right text */}
      <div>
        <hr />
        <p className="py-5 text-sm text-center">
          Copyright 2024@ Prescripto-All Right Reserved
        </p>
      </div>
    </div>
  );
};

export default Footer;
