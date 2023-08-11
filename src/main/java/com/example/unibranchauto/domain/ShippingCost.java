package com.example.unibranchauto.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum ShippingCost {

    _0_5(0.5, 4800),
    _1_0(1.0, 5500),
    _1_5(1.5, 6250),
    _2_0(2.0, 7000),
    _2_5(2.5, 7750),
    _3_0(3.0, 8500),
    _3_5(3.5, 9250),
    _4_0(4.0, 10000),
    _4_5(4.5, 10750),
    _5_0(5.0, 11500),
    _5_5(5.5, 12250),
    _6_0(6.0, 13000),
    _6_5(6.5, 13750),
    _7_0(7.0, 14500),
    _7_5(7.5, 15250),
    _8_0(8.0, 16000),
    _8_5(8.5, 16750),
    _9_0(9.0, 17500),
    _9_5(9.5, 18250),
    _10_0(10.0, 19000),
    _10_5(10.5, 19600),
    _11_0(11.0, 20200),
    _11_5(11.5, 20800),
    _12_0(12.0, 21400),
    _12_5(12.5, 22000),
    _13_0(13.0, 22600),
    _13_5(13.5, 23200),
    _14_0(14.0, 23800),
    _14_5(14.5, 24400),
    _15_0(15.0, 25000),
    _15_5(15.5, 25600),
    _16_0(16.0, 26200),
    _16_5(16.5, 26800),
    _17_0(17.0, 27400),
    _17_5(17.5, 28000),
    _18_0(18.0, 28600),
    _18_5(18.5, 29200),
    _19_0(19.0, 29800),
    _19_5(19.5, 30400),
    _20_0(20.0, 31000),
    _20_5(20.5, 31600),
    _21_0(21.0, 32200),
    _21_5(21.5, 32800),
    _22_0(22.0, 33400),
    _22_5(22.5, 34000),
    _23_0(23.0, 34600),
    _23_5(23.5, 35200),
    _24_0(24.0, 35800),
    _24_5(24.5, 36400),
    _25_0(25.0, 37000),
    _25_5(25.5, 37600),
    _26_0(26.0, 38200),
    _26_5(26.5, 38800),
    _27_0(27.0, 39400),
    _27_5(27.5, 40000),
    _28_0(28.0, 40600),
    _28_5(28.5, 41200),
    _29_0(29.0, 41800),
    _29_5(29.5, 42400),
    _30_0(30.0, 43000),
    _30_5(30.5, 43600),
    _31_0(31.0, 44200),
    _31_5(31.5, 44800),
    _32_0(32.0, 45400),
    _32_5(32.5, 46000),
    _33_0(33.0, 46600),
    _33_5(33.5, 47200),
    _34_0(34.0, 47800),
    _34_5(34.5, 48400),
    _35_0(35.0, 49000),
    _35_5(35.5, 49600),
    _36_0(36.0, 50200),
    _36_5(36.5, 50800),
    _37_0(37.0, 51400),
    _37_5(37.5, 52000),
    _38_0(38.0, 52600),
    _38_5(38.5, 53200),
    _39_0(39.0, 53800),
    _39_5(39.5, 54400),
    _40_0(40.0, 55000),
    _40_5(40.5, 55600),
    _41_0(41.0, 56200),
    _41_5(41.5, 56800),
    _42_0(42.0, 57400),
    _42_5(42.5, 58000),
    _43_0(43.0, 58600),
    _43_5(43.5, 59200),
    _44_0(44.0, 59800),
    _44_5(44.5, 60400),
    _45_0(45.0, 61000),
    _45_5(45.5, 61600),
    _46_0(46.0, 62200),
    _46_5(46.5, 62800),
    _47_0(47.0, 63400),
    _47_5(47.5, 64000),
    _48_0(48.0, 64600),
    _48_5(48.5, 65200),
    _49_0(49.0, 65800),
    _49_5(49.5, 66400),
    _50_0(50.0, 67000),
    _50_5(50.5, 67600),
    _51_0(51.0, 68200),
    _51_5(51.5, 68800),
    _52_0(52.0, 69400),
    _52_5(52.5, 70000),
    _53_0(53.0, 70600),
    _53_5(53.5, 71200),
    _54_0(54.0, 71800),
    _54_5(54.5, 72400),
    _55_0(55.0, 73000),
    _55_5(55.5, 73600),
    _56_0(56.0, 74200),
    _56_5(56.5, 74800),
    _57_0(57.0, 75400),
    _57_5(57.5, 76000),
    _58_0(58.0, 76600),
    _58_5(58.5, 77200),
    _59_0(59.0, 77800),
    _59_5(59.5, 78400),
    _60_0(60.0, 79000),
    _60_5(60.5, 79600),
    _61_0(61.0, 80200),
    _61_5(61.5, 80800),
    _62_0(62.0, 81400),
    _62_5(62.5, 82000),
    _63_0(63.0, 82600),
    _63_5(63.5, 83200),
    _64_0(64.0, 83800),
    _64_5(64.5, 84400),
    _65_0(65.0, 85000),
    _65_5(65.5, 85600),
    _66_0(66.0, 86200),
    _66_5(66.5, 86800),
    _67_0(67.0, 87400),
    _67_5(67.5, 88000),
    _68_0(68.0, 88600),
    _68_5(68.5, 89200),
    _69_0(69.0, 89800),
    _69_5(69.5, 90400),
    _70_0(70.0, 91000),
    _70_5(70.5, 91600),
    _71_0(71.0, 92200),
    _71_5(71.5, 92800),
    _72_0(72.0, 93400),
    _72_5(72.5, 94000),
    _73_0(73.0, 94600),
    _73_5(73.5, 95200),
    _74_0(74.0, 95800),
    _74_5(74.5, 96400),
    _75_0(75.0, 97000),
    _75_5(75.5, 97600),
    _76_0(76.0, 98200),
    _76_5(76.5, 98800),
    _77_0(77.0, 99400),
    _77_5(77.5, 100000),
    _78_0(78.0, 100600),
    _78_5(78.5, 101200),
    _79_0(79.0, 101800),
    _79_5(79.5, 102400),
    _80_0(80.0, 103000),
    _80_5(80.5, 103600),
    _81_0(81.0, 104200),
    _81_5(81.5, 104800),
    _82_0(82.0, 105400),
    _82_5(82.5, 106000),
    _83_0(83.0, 106600),
    _83_5(83.5, 107200),
    _84_0(84.0, 107800),
    _84_5(84.5, 108400),
    _85_0(85.0, 109000),
    _85_5(85.5, 109600),
    _86_0(86.0, 110200),
    _86_5(86.5, 110800),
    _87_0(87.0, 111400),
    _87_5(87.5, 112000),
    _88_0(88.0, 112600),
    _88_5(88.5, 113200),
    _89_0(89.0, 113800),
    _89_5(89.5, 114400),
    _90_0(90.0, 115000),
    _90_5(90.5, 115600),
    _91_0(91.0, 116200),
    _91_5(91.5, 116800),
    _92_0(92.0, 117400),
    _92_5(92.5, 118000),
    _93_0(93.0, 118600),
    _93_5(93.5, 119200),
    _94_0(94.0, 119800),
    _94_5(94.5, 120400),
    _95_0(95.0, 121000),
    _95_5(95.5, 121600),
    _96_0(96.0, 122200),
    _96_5(96.5, 122800),
    _97_0(97.0, 123400),
    _97_5(97.5, 124000),
    _98_0(98.0, 124600),
    _98_5(98.5, 125200),
    _99_0(99.0, 125800),
    _99_5(99.5, 126400),
    _100_0(100.0, 127000),
    _100_5(100.5, 127600),
    _101_0(101.0, 128200),
    _101_5(101.5, 128800),
    _102_0(102.0, 129400),
    _102_5(102.5, 130000),
    _103_0(103.0, 130600),
    _103_5(103.5, 131200),
    _104_0(104.0, 131800),
    _104_5(104.5, 132400),
    _105_0(105.0, 133000),
    _105_5(105.5, 133600),
    _106_0(106.0, 134200),
    _106_5(106.5, 134800),
    _107_0(107.0, 135400),
    _107_5(107.5, 136000),
    _108_0(108.0, 136600),
    _108_5(108.5, 137200),
    _109_0(109.0, 137800),
    _109_5(109.5, 138400),
    _110_0(110.0, 139000),
    _110_5(110.5, 139600),
    _111_0(111.0, 140200),
    _111_5(111.5, 140800),
    _112_0(112.0, 141400),
    _112_5(112.5, 142000),
    _113_0(113.0, 142600),
    _113_5(113.5, 143200),
    _114_0(114.0, 143800),
    _114_5(114.5, 144400),
    _115_0(115.0, 145000),
    _115_5(115.5, 145600),
    _116_0(116.0, 146200),
    _116_5(116.5, 146800),
    _117_0(117.0, 147400),
    _117_5(117.5, 148000),
    _118_0(118.0, 148600),
    _118_5(118.5, 149200),
    _119_0(119.0, 149800),
    _119_5(119.5, 150400),
    _120_0(120.0, 151000),
    _120_5(120.5, 151600),
    _121_0(121.0, 152200),
    _121_5(121.5, 152800),
    _122_0(122.0, 153400),
    _122_5(122.5, 154000),
    _123_0(123.0, 154600),
    _123_5(123.5, 155200),
    _124_0(124.0, 155800),
    _124_5(124.5, 156400),
    _125_0(125.0, 157000),
    _125_5(125.5, 157600),
    _126_0(126.0, 158200),
    _126_5(126.5, 158800),
    _127_0(127.0, 159400),
    _127_5(127.5, 160000),
    _128_0(128.0, 160600),
    _128_5(128.5, 161200),
    _129_0(129.0, 161800),
    _129_5(129.5, 162400),
    _130_0(130.0, 163000);

    private Double weight; // tag
    private Integer cost;

    private static final Map<Double, ShippingCost> WEIGHT = new HashMap<>();

    static {
        for (ShippingCost e: values()) {
            WEIGHT.put(e.weight, e);
        }
    }

    public static ShippingCost valueOfWeight(Double weight) {
        return WEIGHT.get(weight);
    }

    public static ShippingCost valueOfCost(Integer cost) {
        for (ShippingCost e : values()) {
            if (e.cost.equals(cost)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Invalid cost: " + cost);
    }


}