package com.vitube.online_learning.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstructorStatisticResponse implements Comparable<InstructorStatisticResponse>{
    private int month;
    private int year;
    private int totalRegistrations;
    private float totalEarnings;

    @Override
    public int compareTo(InstructorStatisticResponse o) {
        if (this.year != o.year) {
            return Integer.compare(this.year, o.year);
        } else {
            return Integer.compare(this.month, o.month);
        }
    }
}
