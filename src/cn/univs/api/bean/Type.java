package cn.univs.api.bean;

public class Type {
	// // 1 新闻，2 组图，3 链接，4 视频，
	// // 7 活动，8 投票，9 调查，10 专题
	// news(1), images(2), link(3), video(4), activity(7), vote(8), survey(9),
	// special(
	// 10);
	private int index;

	public Type(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		String name = null;
		switch (index) {
		case 1:
			name = "新闻";
			break;
		case 2:
			name = "组图";
			break;
		case 3:
			name = "链接";
			break;
		case 4:
			name = "视频";
			break;
		case 7:
			name = "活动";
			break;
		case 8:
			name = "投票";
			break;
		case 9:
			name = "调查";
			break;
		case 10:
			name = "专题";
			break;
		}
		return name;
	}
}