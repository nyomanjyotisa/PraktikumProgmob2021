package id.jyotisa.praktikumprogmob2021.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Job implements Parcelable {
    String companyName, jobTitle, jobDesc, location, jobType, salary, benefits;

    public Job(String companyName, String jobTitle, String jobDesc, String location, String jobType, String salary, String benefits){
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.jobDesc = jobDesc;
        this.location = location;
        this.jobType = jobType;
        this.salary = salary;
        this.benefits = benefits;
    }

    public String getCompanyName() { return companyName; }
    public String getJobTitle() { return jobTitle; }
    public String getJobDesc() { return jobDesc; }
    public String getLocation() { return location; }
    public String getJobType() { return jobType; }
    public String getSalary() { return salary; }
    public String getBenefits() { return benefits; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.companyName);
        dest.writeString(this.jobTitle);
        dest.writeString(this.jobDesc);
        dest.writeString(this.location);
        dest.writeString(this.jobType);
        dest.writeString(this.salary);
        dest.writeString(this.benefits);
    }

    protected Job(Parcel in) {
        companyName = in.readString();
        jobTitle = in.readString();
        jobDesc = in.readString();
        location = in.readString();
        jobType = in.readString();
        salary = in.readString();
        benefits = in.readString();
    }

    public static final Creator<Job> CREATOR = new Creator<Job>() {
        @Override
        public Job createFromParcel(Parcel in) {
            return new Job(in);
        }

        @Override
        public Job[] newArray(int size) {
            return new Job[size];
        }
    };
}
