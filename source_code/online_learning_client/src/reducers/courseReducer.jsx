import { getCourses } from "utils/CoursesUtil";

export const courseReducer = (state = [], action) => {

    var freeCourses = [];
    var plusCourses = [];
    switch (action.type) {
        case "SEARCH":
          const key = action.payload.key.toLowerCase();
          freeCourses = state.freeCourses.filter(course => course.name.toLowerCase().includes(key));
          plusCourses = state.plusCourses.filter(course => course.name.toLowerCase().includes(key));
          return {
              freeCourses: freeCourses,
              plusCourses: plusCourses
          };
        
        default:
            getCourses("free")
                .then((response) => {
                    freeCourses = response.data;
                })
                .catch((error) => {
                    console.error("Error fetching free courses:", error);
                });
            getCourses("plus")
                .then((response) => {
                    plusCourses = response.data;
                })
                .catch((error) => {
                    console.error("Error fetching plus courses:", error);
                });
            return {
                freeCourses: freeCourses,
                plusCourses: plusCourses
            };
    }
}