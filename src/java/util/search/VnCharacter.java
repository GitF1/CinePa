/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.search;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC
 */
public class VnCharacter {

    public static List<String> movieTitles = new ArrayList<>(List.of(
            "Bố Già", "Lật Mặt", "Mắt Biếc", "Em Là Bà Nội Của Anh", "Bố Là Tất Cả",
            "Ba Người Con", "Hai Phượng", "Cua Lại Vợ Bầu", "Chị Mười Ba", "Lôi Báo",
            "Song Lang", "Về Nhà Đi Con", "Trạng Tí", "Ròm", "Dòng Máu Anh Hùng",
            "Tháng Năm Rực Rỡ", "Để Mai Tính", "Nắng", "Đôi Mắt Âm Dương", "Sài Gòn Anh Yêu Em",
            "Cô Ba Sài Gòn", "Em Chưa 18", "Tèo Em", "Anh Thầy Ngôi Sao", "Tiệc Trăng Máu",
            "Tình Yêu Từ Hai Phía", "Vệ Sĩ Sài Gòn", "Gái Già Lắm Chiêu", "Trạng Quỳnh", "Ống Kính Sát Nhân",
            "798Mười", "Số Nhọ", "Cô Gái Đến Từ Hôm Qua", "Lời Nguyền Huyết Ngải", "Chàng Vợ Của Em",
            "Siêu Sao Siêu Ngố", "Tháng 5 Để Dành", "Mỹ Nhân Kế", "Bụi Đời Chợ Lớn", "Tôi Thấy Hoa Vàng Trên Cỏ Xanh",
            "Găng Tay Đỏ", "Áo Lụa Hà Đông", "Nhà Có 5 Nàng Tiên", "Vợ Ơi... Em Ở Đâu?", "Thiên Mệnh Anh Hùng",
            "Long Ruồi", "Hotboy Nổi Loạn", "Lấy Chồng Người Ta", "Chàng Trai Năm Ấy", "Kẻ Chơi Xấu",
            "Oan Hồn", "Quả Tim Máu", "Tấm Cám: Chuyện Chưa Kể", "Fan Cuồng", "Cô Dâu Đại Chiến",
            "Nhà Có 5 Nàng Tiên", "49 Ngày", "Lạc Giới", "Dạ Cổ Hoài Lang", "Nắng 2",
            "Cạm Bẫy: Hơi Thở Của Quỷ", "Yêu", "12 Chòm Sao: Vẽ Đường Cho Yêu Chạy", "Đời Cho Ta Bao Lần Đôi Mươi", "Ba Vợ Cưới Vợ Ba",
            "Yêu Em Bất Chấp", "Tình Đầu Thơ Ngây", "Chạy Đi Rồi Tính", "Truy Sát", "Truyền Thuyết Về Quán Tiên",
            "Siêu Trộm", "Cho Em Gần Anh Thêm Chút Nữa", "Tháng Năm Dữ Dội", "Dạ Cổ Hoài Lang", "Oan Hồn",
            "Truyền Thuyết Về Quán Tiên", "Lửa Phật", "Cú Và Chim Se Sẻ", "Tâm Sự Bà Bầu", "Chí Phèo Ngoại Truyện",
            "Vòng Eo 56", "Sứ Mệnh Trái Tim", "Trăng Nơi Đáy Giếng", "Tiên Nữ Không Kiêng Cữ", "Khi Con Là Nhà",
            "Ngốc Ơi Tuổi 17", "Lật Mặt 2: Phim Trường", "Quý Cô Thừa Kế", "Cậu Bé Rồng", "Ngôi Nhà Bươm Bướm",
            "Phía Cuối Cầu Vồng", "Ông Ngoại Tuổi 30", "Người Lạ Ơi", "Trăng Máu", "Thiên Thần Hộ Mệnh",
            "Lật Mặt 3: Ba Chàng Khuyết", "Bầu Trời Trong Trẻo", "Căn Hộ 69", "Trăng Nơi Đáy Giếng", "Tình Khúc Bạch Dương", "Hành Động", "Micheal"
    ));
    private static final String[] VIETNAMESE_CHARACTERS = {
        "a", "á", "à", "ả", "ã", "ạ", "ă", "ắ", "ằ", "ẳ", "ẵ", "ặ", "â", "ấ", "ầ", "ẩ", "ẫ", "ậ",
        "e", "é", "è", "ẻ", "ẽ", "ẹ", "ê", "ế", "ề", "ể", "ễ", "ệ",
        "i", "í", "ì", "ỉ", "ĩ", "ị",
        "o", "ó", "ò", "ỏ", "õ", "ọ", "ô", "ố", "ồ", "ổ", "ỗ", "ộ", "ơ", "ớ", "ờ", "ở", "ỡ", "ợ",
        "u", "ú", "ù", "ủ", "ũ", "ụ", "ư", "ứ", "ừ", "ử", "ữ", "ự",
        "y", "ý", "ỳ", "ỷ", "ỹ", "ỵ",
        "d", "đ",
        // Uppercase
        "A", "Á", "À", "Ả", "Ã", "Ạ", "Ă", "Ắ", "Ằ", "Ẳ", "Ẵ", "Ặ", "Â", "Ấ", "Ầ", "Ẩ", "Ẫ", "Ậ",
        "E", "É", "È", "Ẻ", "Ẽ", "Ẹ", "Ê", "Ế", "Ề", "Ể", "Ễ", "Ệ",
        "I", "Í", "Ì", "Ỉ", "Ĩ", "Ị",
        "O", "Ó", "Ò", "Ỏ", "Õ", "Ọ", "Ô", "Ố", "Ồ", "Ổ", "Ỗ", "Ộ", "Ơ", "Ớ", "Ờ", "Ở", "Ỡ", "Ợ",
        "U", "Ú", "Ù", "Ủ", "Ũ", "Ụ", "Ư", "Ứ", "Ừ", "Ử", "Ữ", "Ự",
        "Y", "Ý", "Ỳ", "Ỷ", "Ỹ", "Ỵ",
        "D", "Đ"
    };
}
