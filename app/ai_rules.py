import json
from pathlib import Path

DATA_FILE = Path("custom_ai_data.json")

def load_rules():
    if not DATA_FILE.exists():
        return []
    return json.loads(DATA_FILE.read_text(encoding="utf-8"))

def save_rules(rules):
    DATA_FILE.write_text(json.dumps(rules, ensure_ascii=False, indent=2), encoding="utf-8")

def add_rule(trigger, reply):
    rules = load_rules()
    rules.append({"trigger": trigger.strip().lower(), "reply": reply.strip()})
    save_rules(rules)

def ask_ai(prompt):
    q = prompt.strip().lower()
    for item in load_rules():
        if item["trigger"] and item["trigger"] in q:
            return item["reply"]
    return "अभी इस नियम के लिए कोई जवाब सेव नहीं है। पहले नियम जोड़ें।"

if __name__ == "__main__":
    add_rule("नमस्ते", "नमस्ते! मैं आपका ऑफ़लाइन कस्टम AI हूँ।")
    print(ask_ai("नमस्ते, कैसे हो?"))
