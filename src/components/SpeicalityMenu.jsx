import React from "react";
import { specialityData } from "../assets/assets_frontend/assets";
import { Link } from "react-router-dom";

const SpeicalityMenu = () => {
  return (
    <div
      id="speciality"
      className="flex flex-col items-center gap- py-16 text-gray-800"
    >
      <h1 className="text-3xl font-medium">Find by Speciality</h1>
      <p className="sm:w-1/3 text-center text-sm py-3">
        Simply browse through our extensive list of trusted doctors, Schedule
        your appointment hassle-free
      </p>
      <div className="flex sm:justify-center gap-4 w-full overflow-scroll py-8 ">
        {specialityData.map((item, index) => (
          <Link
            onClick={() => scrollTo(0, 0)}
            className="flex flex-col items-center text-xs cursor-pointer flex-shrin-0
          hover:translate-y-[-10px] transition-all duration-500
          "
            key={index}
            to={`/doctors/${item.speciality}`}
          >
            <img className="w-16 sm:w-24 mb-2" src={item.image} />
            <p>{item.speciality}</p>
          </Link>
        ))}
      </div>
    </div>
  );
};

export default SpeicalityMenu;
