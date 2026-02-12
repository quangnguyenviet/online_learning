import styles from "../Dashboard.module.scss";
import { MdShowChart } from "react-icons/md";

export default function ChartSection({ chartFilter, onFilterChange, chartData, chartLabels }) {
	console.log("Rendering ChartSection with filter:", chartFilter);
	console.log("Chart data:", chartData);
	console.log("Chart labels:", chartLabels);
	return (
		<section className={styles.chartSection}>
			<div className={styles.sectionHeader}>
				<h2>
					<MdShowChart size={24} style={{ marginRight: "8px", verticalAlign: "middle" }} />
					Số học sinh đăng ký
				</h2>
				<div className={styles.chartFilters}>
					<button 
						className={chartFilter === "day" ? styles.filterActive : styles.filterBtn}
						onClick={() => onFilterChange("DAY")}
					>
						Ngày
					</button>
					<button 
						className={chartFilter === "week" ? styles.filterActive : styles.filterBtn}
						onClick={() => onFilterChange("WEEK")}
					>
						Tuần
					</button>
					<button 
						className={chartFilter === "month" ? styles.filterActive : styles.filterBtn}
						onClick={() => onFilterChange("MONTH")}
					>
						Tháng
					</button>
				</div>
			</div>
			<div className={styles.chartContainer}>
				<div className={styles.simpleChart}>
					{chartData[chartFilter].map((value, idx) => (
						<div key={idx} className={styles.chartBar}>
							<div 
								className={styles.bar} 
								style={{ height: `${(value / Math.max(...chartData[chartFilter])) * 100}%` }}
							>
								<span className={styles.barValue}>{value}</span>
							</div>
							<span className={styles.barLabel}>
								{chartLabels[chartFilter][idx]}
							</span>
						</div>
					))}
				</div>
			</div>
		</section>
	);
}
