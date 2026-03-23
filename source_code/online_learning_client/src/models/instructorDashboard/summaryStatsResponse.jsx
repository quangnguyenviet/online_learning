export class SummaryStatsResponse {
    constructor({ totalCourses, totalPublishedCourses,
         totalStudents, totalRevenue, averageRating }) {
        this.totalCourses = totalCourses;
        this.totalPublishedCourses = totalPublishedCourses;
        this.totalStudents = totalStudents;
        this.totalRevenue = totalRevenue;
        this.averageRating = averageRating;
    }
}