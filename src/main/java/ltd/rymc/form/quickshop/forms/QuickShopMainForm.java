package ltd.rymc.form.quickshop.forms;

import ltd.rymc.form.quickshop.form.RCustomForm;
import ltd.rymc.form.quickshop.form.RForm;
import ltd.rymc.form.quickshop.shop.QuickShop;
import ltd.rymc.form.quickshop.utils.InputUtil;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.response.CustomFormResponse;

public class QuickShopMainForm extends RCustomForm {

    public QuickShopMainForm(Player player, RForm previousForm, QuickShop shop) {
        super(player, previousForm);
        title("商店選單");
        input("請輸入要購買/出售的物品數量\n物品價格:" + shop.getPrice() + "元", "§7整數");
    }

    @Override
    public void onValidResult(CustomForm form, CustomFormResponse response) {
        String input = response.asInput(0);
        if (!InputUtil.checkInput(input)) {
            return;
        }
        bukkitPlayer.chat(input);
    }
}
